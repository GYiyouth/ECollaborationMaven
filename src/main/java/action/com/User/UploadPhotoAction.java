package action.com.User;

import action.com.AbstractAction;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pojo.DAO.UserDAO;
import pojo.valueObject.domain.UserVO;
import tool.BeanFactory;

import java.io.*;

/**
 * 上传图片
 * Created by geyao on 2017/4/19.
 */
@Controller
public class UploadPhotoAction extends AbstractAction {

    //注意，file并不是指前端jsp上传过来的文件本身，而是文件上传过来存放在临时文件夹下面的文件
    private File file;

    //提交过来的file的名字
    private String fileFileName;

    //提交过来的file的MIME类型
    private String fileContentType;

    //想让文件存储在哪里，就直接写在这里
    private String savePath;
    {
        String homePath = System.getenv("HOME");
        savePath = homePath + "/ECollaboration/upload/photos";
    }
    @Autowired
    private UserDAO userDAO;

    public String execute() throws Exception{
        JSONObject jsonObject = BeanFactory.getJSONO();

        String role = session.get("role").toString();
        UserVO userVO = (UserVO) session.get(role + "VO");
        File parentPath = new File(savePath);
        if (!parentPath.exists())
            parentPath.mkdirs();

        String fileName = userVO.getId() + ".png";
        File photo = new File(savePath + "/" + userVO.getId() + ".png");
        if (photo.exists())
            photo.delete();
        uploadFile(savePath, fileName, file);

        userDAO.update(userVO);
        jsonObject.put("result", "success");
        return null;
    }

    public String uploadFile(String savePath, String fileName, File tempFile) throws Exception {

        try {
            File tempSavePath = new File(savePath);
            if (!tempSavePath.exists())
                tempSavePath.mkdirs();

            OutputStream os = new FileOutputStream(new File(savePath, fileName));
            InputStream is = new FileInputStream(tempFile);


            byte[] buffer = new byte[500];
            int length = 0;

            while (-1 != (length = is.read(buffer, 0, buffer.length))) {
                os.write(buffer);
            }

            os.close();
            is.close();

            return "success";
        }catch (Exception e){
            e.printStackTrace();
            throw e;

        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }
}
