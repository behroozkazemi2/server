package com.behrouz.server.component;


import com.behrouz.server.model.Document.DocumentEntity;
import com.behrouz.server.repository.Document.DocumentRepository;
import com.behrouz.server.rest.request.IdName;
import com.behrouz.server.utils.ArraysUtil;
import com.behrouz.server.utils.StringsUtil;
import com.behrouz.server.values.UploadUtil;
import com.behrouz.server.values.UploaderValues;
import com.behrouz.server.modelOption.DocumentTypeOption;
import io.netty.util.internal.StringUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentComponent {

    @Autowired
    private DocumentRepository documentRepository;

    public IdName uploadDocument(MultipartFile[] files) throws Exception {
        List<DocumentEntity> documentList;
        documentList = new ArrayList<>();

        for (MultipartFile file : files) {

            if (file == null || file.isEmpty() || StringUtil.isNullOrEmpty(file.getOriginalFilename())) {
                throw new Exception("فایل مورد نظر به درستی آپلود نشده است.");
            }

//            if (file.getOriginalFilename().split("\\.").length > 2) {
//                System.out.println("Exception " + file.getOriginalFilename());
//                throw new Exception("امکان آپلود این فایل وجود ندارد. لطفا ابتدا نام فایل را تغییر دهید.");
//            }

            if ( !StringsUtil.checkFileExtensionImage( file ) ) {
                System.out.println("Exception " + file.getOriginalFilename());
                throw new Exception("امکان آپلود فایل با این پسوند وجود ندارد.");
            }

            long typeId = getTypeIdFromExtension( FilenameUtils.getExtension(file.getOriginalFilename()) );

            String directory = UploaderValues.SAVE_PATH;

            String newFilename = UploadUtil.calculateFilename( directory, file.getOriginalFilename().replaceAll(":","_").replaceAll(" ", "_") );

            //check if filename already exist and generate a new one
//            String newFilename = UploadUtil.calculateFilename(directory, file.getOriginalFilename());

            documentList.add(new DocumentEntity(
                    typeId,
                    directory,
                    newFilename,
                    file.getSize()
            ));

            Files.copy(file.getInputStream(), Paths.get(directory).resolve(newFilename));
        }

        documentRepository.saveAll(documentList);
        return ArraysUtil.isNullOrEmpty(documentList) ? new IdName(0, "") : new IdName(documentList.get(0).getId(), documentList.get(0).getName());
    }

    public long getTypeIdFromExtension(String extension) throws Exception {

        switch (extension) {
            case "png":
                return DocumentTypeOption.PNG.getId();
            case "jpg":
                return DocumentTypeOption.JPG.getId();
            case "jpeg":
                return DocumentTypeOption.JPEG.getId();
            case "pdf":
                return DocumentTypeOption.PDF.getId();
        }

        return 0;
//        throw new Exception("پسوند نامعتبر");
    }


}
