package utils.engine.data;

import java.util.Arrays;

import utils.engine.data.enums.Regime;
import utils.engine.data.enums.RegimeAligne;

public class UserChecklistGenerationData {

	private MonthAndYear dateDepart;
	private String departement;
	private Regime[] regimes;
	private RegimeAligne[] regimesAlignes;

	public boolean isConjointCollaborateur;
	public boolean isChef;
	public boolean isNSA;
	public boolean isSA;
	public boolean isCarriereLongue;

	public boolean published;
	public boolean isPDF;
	public String regimesInfosJsonStr;

	public UserChecklistGenerationData(final MonthAndYear dateDepart, final String departement, final Regime[] regimes, final RegimeAligne[] regimesAlignes,
			final boolean published, final boolean isPDF, final String regimesInfosJsonStr) {
		this.dateDepart = dateDepart;
		this.departement = departement;
		this.regimes = regimes;
		this.regimesAlignes = regimesAlignes;
		this.published = published;
		this.isPDF = isPDF;
		this.regimesInfosJsonStr = regimesInfosJsonStr;
	}

	private UserChecklistGenerationData() {
	}

	public MonthAndYear getDateDepart() {
		return dateDepart;
	}

	public String getDepartement() {
		return departement;
	}

	public Regime[] getRegimes() {
		return regimes;
	}

	public RegimeAligne[] getRegimesAlignes() {
		return regimesAlignes;
	}

	public static Builder create() {
		return new Builder();
	}

	@Override
	public String toString() {
		return "UserChecklistGenerationData[dateDepart=" + dateDepart + ", departement=" + departement + ", regimes=" + Arrays.toString(regimes)
				+ ", regimesAlignes=" + Arrays.toString(regimesAlignes) + ", isConjointCollaborateur=" + isConjointCollaborateur + ", isChef=" + isChef
				+ ", isNSA=" + isNSA + ", isSA=" + isSA + ", isCarriereLongue=" + isCarriereLongue + ", published=" + published + ", isPDF=" + isPDF
				+ ", regimesInfosJsonStr=" + regimesInfosJsonStr + "]";
	}

	public static class Builder {

		private final UserChecklistGenerationData userChecklistGenerationData = new UserChecklistGenerationData();

		public UserChecklistGenerationData get() {
			return userChecklistGenerationData;
		}

		public Builder withDateDepart(final MonthAndYear dateDepart) {
			userChecklistGenerationData.dateDepart = dateDepart;
			return this;
		}

		public Builder withDepartement(final String departement) {
			userChecklistGenerationData.departement = departement;
			return this;
		}

		public Builder withRegimes(final Regime... regimes) {
			userChecklistGenerationData.regimes = regimes;
			return this;
		}

		public Builder withRegimesAlignes(final RegimeAligne... regimesAlignes) {
			userChecklistGenerationData.regimesAlignes = regimesAlignes;
			return this;
		}

		public Builder withPublished(final boolean published) {
			userChecklistGenerationData.published = published;
			return this;
		}

		public Builder withIsPDF(final boolean isPDF) {
			userChecklistGenerationData.isPDF = isPDF;
			return this;
		}

		public Builder withSA(final boolean isSA) {
			userChecklistGenerationData.isSA = isSA;
			return this;
		}

		public Builder withNSA(final boolean isNSA) {
			userChecklistGenerationData.isNSA = isNSA;
			return this;
		}

		public Builder withConjointCollaborateur(final boolean isConjointCollaborateur) {
			userChecklistGenerationData.isConjointCollaborateur = isConjointCollaborateur;
			return this;
		}

		public Builder withChef(final boolean isChef) {
			userChecklistGenerationData.isChef = isChef;
			return this;
		}

		public Builder withCarriereLonge(final boolean isCarriereLongue) {
			userChecklistGenerationData.isCarriereLongue = isCarriereLongue;
			return this;
		}

		public Builder withRegimesInfosJsonStr(final String regimesInfosJsonStr) {
			userChecklistGenerationData.regimesInfosJsonStr = regimesInfosJsonStr;
			return this;
		}

	}

}
