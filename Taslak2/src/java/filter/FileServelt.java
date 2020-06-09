/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import controller.PrintDocumentController;
import entity.GameDocument;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Cyber Micro
 */

public class FileServelt {

    @Inject
    private GameDocument game_doc;
    @Inject
    private PrintDocumentController resim_yol;

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String file = request.getPathInfo();
        if (file.contains("apk") || file.contains("exe") || file.contains("tar")) {//hangi dosya uzantılarının kabul goreceği yazılmış.
            File f = new File(game_doc.getFilePath()+ file);
            Files.copy(f.toPath(), response.getOutputStream());
        } else if (file.contains("jpg") || file.contains("png")) {//resim için **
            File f = new File(resim_yol.getUploadTo() + file);
            Files.copy(f.toPath(), response.getOutputStream());
        }

    }
}
