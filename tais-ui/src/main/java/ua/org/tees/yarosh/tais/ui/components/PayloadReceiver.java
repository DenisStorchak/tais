package ua.org.tees.yarosh.tais.ui.components;

import com.vaadin.ui.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.util.function.Consumer;

import static com.vaadin.ui.Upload.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class PayloadReceiver implements Receiver, SucceededListener, FailedListener {

    private static final Logger log = LoggerFactory.getLogger(PayloadReceiver.class);
    private static final String RANDOM_PREFIX_SEPARATOR = "_";
    private static final String DEBUG_TRYING_TO_UPLOAD_WITH_MIME = "Trying to upload [{}] with mime [{}]";
    private static final String DEBUG_GENERATED_PATH = "Generated path [{}]";
    private static final String ERROR_FILENAME_IS_NULL = "Filename is null";
    private static final String ERROR_CANT_CREATE_DIRECTORY = "Can't create [{}] directory, payload can't be persisted";
    private static final String ERROR_CANT_STORE_PAYLOAD_REASON = "Can't store payload. [REASON: {}]";
    private static final String ERROR_PAYLOAD_UPLOADING_FAILURE_REASON = "Payload uploading failure. [REASON: {}]";
    private static final String SUCCESS_MESSAGE = "Загружено";
    private static final String FAILURE_MESSAGE = "Загрузка не удалась";
    private Consumer<File> successConsumer;

    private String pathPrefix;
    private File storingFile;

    public PayloadReceiver(String pathPrefix, Consumer<File> successConsumer) {
        this.pathPrefix = pathPrefix;
        this.successConsumer = successConsumer;
    }

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        log.debug(DEBUG_TRYING_TO_UPLOAD_WITH_MIME, filename, mimeType);
        if (filename == null) {
            log.error(ERROR_FILENAME_IS_NULL);
            return null;
        }
        File parent = FileSystems.getDefault().getPath(pathPrefix).toFile();
        if (!parent.exists() && !parent.mkdirs()) {
            log.error(ERROR_CANT_CREATE_DIRECTORY, parent.getPath());
        }
        storingFile = createPayloadFile(filename);
        try {
            if (!storingFile.createNewFile()) {
                storingFile = createPayloadFile(filename);
            }
            return new FileOutputStream(storingFile);
        } catch (IOException e) {
            log.error(ERROR_CANT_STORE_PAYLOAD_REASON, e);
        }
        return null;
    }

    private File createPayloadFile(String filename) {
        File storingFile;
        storingFile = FileSystems.getDefault().getPath(pathPrefix,
                randomAlphanumeric(6).concat(RANDOM_PREFIX_SEPARATOR).concat(filename)).toFile();
        log.debug(DEBUG_GENERATED_PATH, storingFile);
        return storingFile;
    }

    @Override
    public void uploadSucceeded(SucceededEvent event) {
        if (successConsumer != null) {
            successConsumer.accept(storingFile);
        }
        Notification.show(SUCCESS_MESSAGE, Notification.Type.HUMANIZED_MESSAGE);
    }

    @Override
    public void uploadFailed(FailedEvent event) {
        log.error(ERROR_PAYLOAD_UPLOADING_FAILURE_REASON, event.getReason());
        Notification.show(FAILURE_MESSAGE);
    }
}
