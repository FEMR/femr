package femr.ui.models.export;

import play.data.validation.Constraints.Validate;
import play.data.validation.Constraints.Validatable;
import play.data.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

@Validate
public class FilterFormViewModel implements Validatable<List<ValidationError>> {

    private List<Integer> missionTripIds = new ArrayList<>();

    public void setMissionTripIds(List<Integer> selectedMissionTripIds) {
        this.missionTripIds = selectedMissionTripIds;
    }

    public List<Integer> getMissionTripIds() {
        return missionTripIds;
    }

    @Override
    public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<>();

        if(missionTripIds.size() == 0){
            errors.add(new ValidationError("missionTripIds", "No trips were seleceted"));
        }

        return errors;
    }
}
