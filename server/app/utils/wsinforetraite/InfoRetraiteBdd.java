package utils.wsinforetraite;

import models.FakeData;
import utils.wsinforetraite.InfoRetraiteResult.InfoRetraiteResultRegime;
import utils.wsinforetraite.InfoRetraiteResult.Status;

public class InfoRetraiteBdd implements InfoRetraite {

	@Override
	public InfoRetraiteResult retrieveAllInformations(final String name, final String nir, final String dateNaissance) {
		final FakeData fakeData = find(name, nir);
		if (fakeData == null) {
			return new InfoRetraiteResult(Status.NOTFOUND, null);
		}
		final String[] regimes = fakeData.regimes.trim().split(",");
		final InfoRetraiteResultRegime[] regimesInfos = new InfoRetraiteResultRegime[regimes.length];
		for (int i = 0; i < regimes.length; i++) {
			final String regime = regimes[i];
			regimesInfos[i] = new InfoRetraiteResultRegime();
			regimesInfos[i].regime = regime + ":nom_regime";
			regimesInfos[i].nom = regime;
			regimesInfos[i].adresse = regime + ":adresse";
			regimesInfos[i].internet = regime + ":internet";
			regimesInfos[i].tel1 = regime + ":tel1";
			regimesInfos[i].tel2 = regime + ":tel2";
			regimesInfos[i].fax = regime + ":fax";
			regimesInfos[i].email1 = regime + ":email1";
			regimesInfos[i].email2 = regime + ":email2";

		}
		return new InfoRetraiteResult(Status.FOUND, regimesInfos);
	}

	private FakeData find(final String name, final String nir) {
		return FakeData.find(name.trim(), nir.replace(" ", ""));
	}

}
