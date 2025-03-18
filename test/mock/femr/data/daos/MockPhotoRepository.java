package mock.femr.data.daos;

import femr.data.daos.core.IPhotoRepository;
import femr.data.models.core.IPatientEncounterPhoto;
import femr.data.models.core.IPhoto;
import mock.femr.data.models.MockPhoto;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class MockPhotoRepository implements IPhotoRepository {

    private List<MockPhoto> mockPhotos = new ArrayList<>();

    public void setMockPhotos(List<MockPhoto> mockPhotos) {
        this.mockPhotos = mockPhotos;
    }

    @Override
    public IPhoto createPhoto(String description, String filePath, byte[] photoData) {
        return null;
    }

    @Override
    public boolean createPhotoOnFilesystem(File image, String filePath) {
        return false;
    }

    @Override
    public boolean createPhotoOnFilesystem(BufferedImage bufferedImage, String filePath) {
        return false;
    }

    @Override
    public IPatientEncounterPhoto createEncounterPhoto(int photoId, int encounterId) {
        return null;
    }

    @Override
    public IPhoto retrievePhotoById(int id) {
        return null;
    }

    @Override
    public List<? extends IPatientEncounterPhoto> retrieveEncounterPhotosByEncounterId(int encounterId) {
        return Collections.emptyList();
    }

    @Override
    public List<? extends IPatientEncounterPhoto> retrieveEncounterPhotosByPhotoId(int photoId) {
        return Collections.emptyList();
    }

    @Override
    public IPhoto updatePhotoDescription(int id, String description) {
        return null;
    }

    @Override
    public IPhoto updatePhotoFilePath(int id, String filePath) {
        return null;
    }

    @Override
    public IPhoto updatePhotoData(int id, byte[] photoData) {
        return null;
    }

    @Override
    public boolean deletePhotoById(int id) {
        return false;
    }

    @Override
    public boolean deletePhotoFromFilesystemById(String filePath) {
        return false;
    }

    @Override
    public boolean deleteEncounterPhotosByPhotoId(int id) {
        return false;
    }

    @Override
    public List<? extends IPhoto> retrievePhotosByPatientId(int patientId) {
        return Collections.emptyList();
    }

    @Override
    public String retrievePhotoContentType(int id) {
        return "";
    }
}
