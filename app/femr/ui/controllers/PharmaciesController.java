package femr.ui.controllers;

import com.google.inject.Inject;
import femr.common.models.IPatientPrescription;
import femr.ui.models.pharmacy.CreateViewModel;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import femr.data.models.PatientPrescription;

public class PharmaciesController extends Controller {

    private final Form<CreateViewModel> createViewModelForm = Form.form(CreateViewModel.class);
    private final IPatientPrescription pharmacyService;

    @Inject
    public PharmaciesController(IPatientPrescription pharmacyService) {
        this.pharmacyService = pharmacyService;
    }
}
