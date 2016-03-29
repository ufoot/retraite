package controllers;

import static controllers.utils.DataUnbinder.unbind;
import static utils.dao.DaoChecklistFactory.createDaoChecklist;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import controllers.data.PostData;
import models.Checklist;
import play.Logger;
import play.modules.pdf.PDF;
import play.templates.Template;
import play.templates.TemplateLoader;
import utils.doc.ChecklistForDoc;
import utils.doc.ChecklistForDocConverter;
import utils.engine.RetraiteEngineFactory;
import utils.engine.data.RenderData;
import utils.mail.MailSenderWithSendGrid;

public class Application extends RetraiteController {

	public static void process(final PostData postData, final String test) {
		if (postData != null) {
			postData.hidden_userStatus = unbind(params.get("postData.hidden_userStatus"));
		}
		final boolean isTest = params._contains("test");
		final RenderData data = RetraiteEngineFactory.create().processToNextStep(postData);
		final String page = getPageNameForGoogleAnalytics(data);
		renderTemplate("Application/steps/" + data.hidden_step + ".html", data, isTest, page);
	}

	public static void sendMail(final PostData postData) {

		final RenderData data = RetraiteEngineFactory.create().processToNextStep(postData);
		data.isPDF = true;

		final String htmlContent = "Bonjour,<br/><br/>Veuillez trouver ci-joint votre checklist !<br/><br/>L'Equipe <b>Parcours Retraite</b>";

		final File file = new File("parcours.pdf");
		final PDF.Options pdfOptions = createPdfOptions();
		PDF.writePDF(file, "Application/pdf.html", pdfOptions, data);

		new MailSenderWithSendGrid().sendMail("Parcours Retraite<envoi.retraite@sgmap.fr>", postData.email, "Mon parcours retraite", htmlContent, file);
		Logger.info("Mail envoyé à " + postData.email + " !");
		ok();
	}

	private static final boolean AS_HTML = false;
	private static final boolean RENDER_PDF_WITH_I_TEXT = false;

	public static void pdf(final PostData postData, final String html) {

		final RenderData data = RetraiteEngineFactory.create().processToNextStep(postData);
		data.isPDF = true;

		if (AS_HTML || params._contains("html")) {
			// Rendu HTML pour mise au point
			render(data);
		}

		setResponseHeaderForPdfContentType();
		setResponseHeaderForAttachedPdf();

		if (RENDER_PDF_WITH_I_TEXT) {
			renderPdfWith_iText(data);
			ok();
		}

		// PDF.renderPDF() ne peut pas être utilisé car il écrase les headers fixés ci-dessous
		renderPdfWithPdfPlayModule(data);
		ok();
	}

	// Méthodes privées

	private static String getPageNameForGoogleAnalytics(final RenderData data) {
		return data.hidden_step + ("displayLiquidateurQuestions".equals(data.hidden_step) ? "_" + data.hidden_liquidateurStep : "");
	}

	private static void setResponseHeaderForPdfContentType() {
		response.setHeader("Content-Type", "application/pdf");
	}

	private static void setResponseHeaderForAttachedPdf() {
		response.setHeader("Content-Disposition", "attachment; filename=\"Mes_demarches_retraite.pdf\"");
	}

	private static void renderPdfWith_iText(final RenderData data) {
		final Template template = TemplateLoader.load(template("Application/pdf.html"));
		final String hmltResult = template.render(createMapWithParams(data));
		final String hmltResultWithFullCssPath = completePublicPathWithCurrentDirectoryPath(hmltResult);
		try {
			final Document document = new Document();
			final PdfWriter writer = PdfWriter.getInstance(document, response.out);
			document.open();
			final InputStream inputStreamWithHtml = new ByteArrayInputStream(hmltResultWithFullCssPath.getBytes());
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, inputStreamWithHtml);
			document.close();
		} catch (DocumentException | IOException e) {
			Logger.error(e, "Erreur pendant la génération du PDF");
			error(e);
		}
	}

	private static String completePublicPathWithCurrentDirectoryPath(final String hmltResult) {
		return hmltResult.replaceAll("/public/", getCurrentPath() + "/public/");
	}

	private static Map<String, Object> createMapWithParams(final RenderData data) {
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", data);
		return map;
	}

	private static void renderPdfWithPdfPlayModule(final RenderData data) {
		PDF.writePDF(response.out, data);
	}

	private static String getCurrentPath() {
		final File f = new File(".");
		final String absolutePath = f.getAbsolutePath();
		return absolutePath.substring(0, absolutePath.length() - 2);
	}

	public static void generateDoc(final int checklistId) {
		final Checklist checklistFromBdd = createDaoChecklist().findById(checklistId);
		final ChecklistForDoc checklist = new ChecklistForDocConverter().convert(checklistFromBdd);
		render(checklist);
	}

	private static PDF.Options createPdfOptions() {
		final PDF.Options pdfOptions = new PDF.Options();
		pdfOptions.FOOTER = "<span style='font-size: 0.7em;'>Parcours Retraite</span>";
		return pdfOptions;
	}

}