/*
     fEMR - fast Electronic Medical Records
     Copyright (C) 2014  Team fEMR

     fEMR is free software: you can redistribute it and/or modify
     it under the terms of the GNU General Public License as published by
     the Free Software Foundation, either version 3 of the License, or
     (at your option) any later version.

     fEMR is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with fEMR.  If not, see <http://www.gnu.org/licenses/>. If
     you have any questions, contact <info@teamfemr.org>.
*/
package femr.business.services.system;

import com.google.inject.name.Named;
import femr.business.helpers.LogicDoer;
import femr.business.services.core.IPhotoService;
import femr.common.IItemModelMapper;
import femr.common.models.PatientEncounterItem;
import femr.common.dtos.ServiceResponse;
import com.google.inject.Inject;
import femr.common.models.PhotoItem;
import femr.data.daos.core.IPatientRepository;
import femr.data.daos.core.IPhotoRepository;
import femr.data.models.core.IPatient;
import femr.data.models.core.IPatientEncounterPhoto;
import femr.data.models.core.IPhoto;
import femr.ui.models.medical.EditViewModelPost;
import femr.util.stringhelpers.StringUtils;
import org.apache.commons.codec.binary.Base64;
import play.mvc.Http.MultipartFormData.FilePart;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class PhotoService implements IPhotoService {

    private String _profilePhotoPath;
    private String _encounterPhotoPath;
    private IPhotoRepository photoRepository;
    private IPatientRepository patientRepository;
    private final IItemModelMapper itemModelMapper;

    @Inject
    public PhotoService(IPatientRepository patientRepository,
                        IPhotoRepository photoRepository,
                        @Named("identified") IItemModelMapper itemModelMapper) {

        this.photoRepository = photoRepository;
        this.patientRepository = patientRepository;
        this.itemModelMapper = itemModelMapper;

        this.Init();
    }

    private void Init() {
        File f;
        _profilePhotoPath = LogicDoer.getPatientPhotoPath();

        _encounterPhotoPath = LogicDoer.getMedicalPhotoPath();


        //Ensure folder exists, if not, create it
        f = new File(_profilePhotoPath);
        if (!f.exists())
            f.mkdirs();

        f = new File(_encounterPhotoPath);
        if (!f.exists())
            f.mkdirs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<Boolean> createPatientPhoto(String imageString, int patientId, Boolean deleteFlag) {
        ServiceResponse<Boolean> response = new ServiceResponse<>();

        try {
            IPatient patient = patientRepository.retrievePatientById(patientId);
            String imageFileName = "/Patient_" + patient.getId() + ".jpg";

            if (StringUtils.isNotNullOrWhiteSpace(imageString)) {
                if (patient.getPhoto() == null) {
                    //Create new photo Id record
                    IPhoto pPhoto = photoRepository.createPhoto("", imageFileName);
                    patient.setPhoto(pPhoto);
                    patientRepository.savePatient(patient);
                } else {
                    //Record already exists:
                    //photoId = patient.getPhoto().getId();
                }

                //save image to disk
                String parsedImage = imageString.substring(imageString.indexOf(",") + 1);
                BufferedImage bufferedImage = decodeToImage(parsedImage);
                String filePathTarget = _profilePhotoPath + imageFileName;
                photoRepository.createPhotoOnFilesystem(bufferedImage, filePathTarget);
            } else {
                if (deleteFlag != null)
                    if (deleteFlag && patient.getPhoto() != null) {
                        //delete photo
                        //First make sure the photoId is null in the patient record
                        Integer id = patient.getPhoto().getId();
                        patient.setPhoto(null);
                        patientRepository.savePatient(patient);
                        //Now remove the photo record:
                        this.deletePhotoById(id, _profilePhotoPath);
                    }
            }
            response.setResponseObject(true);
        } catch (Exception ex) {
            response.setResponseObject(false);
            response.addError("HandlePatientPhoto", ex.getMessage());
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<String> retrievePatientPhotoPath(int patientId) {
        ServiceResponse<String> response = new ServiceResponse<>();
        try {
            IPatient patient = patientRepository.retrievePatientById(patientId);
            if (patient.getPhoto() == null) {
                response.setResponseObject(null);
            } else {
                response.setResponseObject(_profilePhotoPath + patient.getPhoto().getFilePath());
            }
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
            return response;
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<String> retrievePhotoPath(int photoId) {
        ServiceResponse<String> response = new ServiceResponse<>();

        try {
            IPhoto photo = photoRepository.retrievePhotoById(photoId);
            response.setResponseObject(_encounterPhotoPath + photo.getFilePath());
        } catch (Exception ex) {
            response.addError("", ex.getMessage());
        }
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<Boolean> createEncounterPhotos(List<FilePart<Object>> encounterImages, PatientEncounterItem patientEncounterItem, EditViewModelPost mod) {
        ServiceResponse<Boolean> sr = new ServiceResponse<>();
        try {
            int count = mod.getPhotoId().size();
            FilePart fp;
            for (Integer i = 0; i < count; i++) {
                Integer id = mod.getPhotoId().get(i);
                if (id == null) {
                    //This is a new image, add it to the DB and filesystem:
                    for (FilePart fp_iterator : encounterImages) {
                        String keyName = fp_iterator.getKey();
                        int leftBracket = keyName.indexOf("[");
                        int rightBracket = keyName.indexOf("]");
                        if (leftBracket >= 0 && rightBracket >= 0) {
                            keyName = keyName.substring(leftBracket + 1, rightBracket);
                            String tempindex = i.toString();

                            if (keyName.equalsIgnoreCase(tempindex)) {
                                saveNewEncounterImage(fp_iterator, patientEncounterItem, mod.getImageDescText().get(i));
                                break;
                            }
                        }
                    }

                } else {
                    Boolean bDelete = mod.getDeleteRequested().get(i);
                    if (bDelete == null)
                        bDelete = false;

                    if (bDelete) {
                        //Delete image
                        if (mod.getPhotoId().get(i) != null)
                            this.deletePhotoById(mod.getPhotoId().get(i), _encounterPhotoPath);
                    } else {
                        //Possibly update the image
                        Boolean bisUpdate = mod.getHasUpdatedDesc().get(i);
                        if (bisUpdate != null) {
                            if (bisUpdate) {
                                Integer photoId = mod.getPhotoId().get(i);
                                if (photoId != null) {
                                    //updateImageDescription(photoId, mod.getImageDescText().get(i));

                                    try {

                                        photoRepository.updatePhotoDescription(photoId, mod.getImageDescText().get(i));

                                    } catch (Exception ex) {

                                        sr.addError("", "error updating description: " + ex.getMessage());
                                    }
                                }


                            }
                        }
                    }

                }
            }
            sr.setResponseObject(true);
        } catch (Exception ex) {
            sr.setResponseObject(false);
            sr.addError("createEncounterPhotos()", ex.getMessage());
        }

        return sr;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ServiceResponse<List<PhotoItem>> retrieveEncounterPhotos(int encounterId) {

        ServiceResponse<List<PhotoItem>> response = new ServiceResponse<>();
        try {

            List<? extends IPatientEncounterPhoto> photoList = photoRepository.retrieveEncounterPhotosByEncounterId(encounterId);
            if (photoList != null) {
                List<PhotoItem> returnList = new ArrayList<>();
                for (IPatientEncounterPhoto pep : photoList) {

                    try {
                        IPhoto savedPhoto = photoRepository.retrievePhotoById(pep.getPhotoId());
                        returnList.add(itemModelMapper.createPhotoItem(savedPhoto.getId(), savedPhoto.getDescription(), savedPhoto.getInsertTS(), femr.ui.controllers.routes.PhotoController.GetPhoto(savedPhoto.getId()).toString()));
                    } catch (Exception ex) {
                        response.addError("", ex.getMessage());
                        return response;
                    }
                }

                response.setResponseObject(returnList);
            }
        } catch (Exception ex) {
            response.addError("photo", ex.getMessage());
        }

        return response;
    }


    private void saveNewEncounterImage(FilePart image, PatientEncounterItem patientEncounter, String descriptionText) {
        try {
            String imageFileName;

            //Create photo record:
            IPhoto pPhoto = photoRepository.createPhoto(descriptionText, "");
            //this is redundant...?
            IPhoto editPhoto = photoRepository.retrievePhotoById(pPhoto.getId());

            //Build the filepath for the image
            imageFileName = "Patient_" + patientEncounter.getPatientId()
                    + "_Enc_" + patientEncounter.getId() + "_Photo_" + editPhoto.getId();

            //Since the photo ID is part of the file name
            //  I am setting the filePath field after the record is created
            photoRepository.updatePhotoFilePath(editPhoto.getId(), imageFileName);

            //Link photo record in photoEncounter table

            photoRepository.createEncounterPhoto(editPhoto.getId(), patientEncounter.getId());


            photoRepository.createPhotoOnFilesystem(image, this._encounterPhotoPath + imageFileName);

        } catch (Exception ex) {

            String test = "uh oh";
        }
    }


    /**
     * Deletes a photo from the filesystem.
     *
     * @param id id of the photo
     * @param filePath directory the photo is in
     * @return
     */
    private ServiceResponse<IPhoto> deletePhotoById(int id, String filePath) {

        IPhoto savedPhoto = photoRepository.retrievePhotoById(id);
        if (savedPhoto != null) {

            photoRepository.deleteEncounterPhotosByPhotoId(savedPhoto.getId());

            photoRepository.deletePhotoFromFilesystemById(filePath + savedPhoto.getFilePath());

            photoRepository.deletePhotoById(savedPhoto.getId());
        }
        ServiceResponse<IPhoto> response = new ServiceResponse<>();

        if (savedPhoto != null) {
            response.setResponseObject(savedPhoto);
        } else {
            response.addError("photo", "photo could not be deleted from the database");
        }

        return response;
    }

    /**
     * Decodes a base64 encoded string to an image
     *
     * @param imageString base64 encoded string that has been parsed to only include imageBytes
     * @return the decoded image
     */
    private static BufferedImage decodeToImage(String imageString) {

        BufferedImage image = null;
        byte[] imageByte;
        try {
            Base64 newDecoder = new Base64();
            byte[] bytes = imageString.getBytes(Charset.forName("UTF-8"));
            imageByte = newDecoder.decode(bytes);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }


}
