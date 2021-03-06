package utils.engine.intern;

import static utils.engine.data.enums.UserStatus.STATUS_CHEF;
import static utils.engine.data.enums.UserStatus.STATUS_CONJOINT_COLLABORATEUR;
import static utils.engine.data.enums.UserStatus.STATUS_NSA;
import static utils.engine.data.enums.UserStatus.STATUS_SA;

import java.util.List;

import utils.engine.data.MonthAndYear;
import utils.engine.data.UserChecklistGenerationData;
import utils.engine.data.enums.Regime;
import utils.engine.data.enums.RegimeAligne;
import utils.engine.data.enums.UserStatus;

public class UserChecklistGenerationDataBuilder {

	public UserChecklistGenerationData build(final MonthAndYear dateDepart, final String departement, final Regime[] regimes,
			final RegimeAligne[] regimesAlignes, final RegimeAligne regimeLiquidateur, final boolean published,
			final boolean isCarriereLongue, final List<UserStatus> userStatus, final boolean isPDF, final String regimesInfosJsonStr) {

		final UserChecklistGenerationData userChecklistGenerationData = new UserChecklistGenerationData(dateDepart, departement, regimes, regimesAlignes,
				published, false, regimesInfosJsonStr);
		userChecklistGenerationData.isConjointCollaborateur = contains(userStatus, STATUS_CONJOINT_COLLABORATEUR);
		userChecklistGenerationData.isChef = contains(userStatus, STATUS_CHEF);
		userChecklistGenerationData.isNSA = contains(userStatus, STATUS_NSA);
		userChecklistGenerationData.isSA = contains(userStatus, STATUS_SA);
		// [XN-29/03/2016-En attendant de remettre les questions complémentaires, on force l'affichage des chapitres]
		userChecklistGenerationData.isCarriereLongue = isCarriereLongue;
		userChecklistGenerationData.isPDF = isPDF;
		return userChecklistGenerationData;
	}

	private boolean contains(final List<UserStatus> userStatus, final UserStatus status) {
		return userStatus != null && userStatus.contains(status);
	}

}
