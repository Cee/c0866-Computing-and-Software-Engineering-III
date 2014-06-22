package excalibur.game.data.gamedata;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class setpreference {
	private static final String SETFILENAME="setting.tat";
	private static final String PICTUREPATH="default.jpg";
	private static final String PPATHS[]={};
	
	/**
	 * 
	 * @param sp
	 * @return ���������Ϣ��0��ʾ�ɹ���1��ʾ�ļ�δ�ҵ���2��ʾ��д����
	 */
	public static int setpreferrence(setPO sp){
//		String pn=PICTUREPATH;
//		
//		try {
//			pn=pictureValidate(sp.getPictureName());			
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			return 2;
//		}

		
		setPO result=new setPO(sp.getPictureName(),sp.getIsBackgroundMusicOpen(),sp.getIsSoundEffectOpen(),sp.getIsVertical(),sp.getUsrName()); 
		File settingFile=new File(SETFILENAME);
		ObjectOutputStream oout;
		try {
			if(!settingFile.exists()){
				settingFile.createNewFile();
			}
			oout = new ObjectOutputStream(new FileOutputStream(settingFile));
			oout.writeObject(result);
			oout.close();
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return 1;
		}
	}
	
	/**
	 * 
	 * @param imagePath setpo�е�ͼƬ������������·��
	 * @return ���Ƶ���ϷĿ¼����ļ���
	 * @throws IOException 
	 */
	private static String pictureValidate(String imagePath) throws IOException{
/*		File old = new File(imagePath);
		System.out.println(imagePath);
		
		String t[]=imagePath.split("\\\\");
		String newpath=t[t.length-1];
		
		newpath=IMAGEPATH+newpath;
//		if the image is exactly in defualt folder
		if(newpath.equals(imagePath)&&old.exists()){
        	return newpath;
        }
		File newImage = new File(IMAGEPATH+newpath);
        FileInputStream imageInput = null;
        FileOutputStream imageOutput = null;
        
        imageInput = new FileInputStream(old);                
            if(!newImage.exists() ){
                //newImage.mkdir();
                newImage.createNewFile();
            }
            imageOutput = new FileOutputStream(newImage);
            byte[] temp = new byte[1000];
            int size = imageInput.read(temp);
            while (size != -1) {
            	imageOutput.write(temp);
	            size = imageInput.read(temp);
            }
            System.out.println("the File Copy is success!");
            imageInput.close();
            imageOutput.close();
        return newpath;
   */
		for(String s:PPATHS){
			if(s.equals(imagePath)){
				return imagePath;
			}
		}
		return PICTUREPATH;
    }
		
	public static setPO getSettings(){
		File file=new File(SETFILENAME);
		ObjectInputStream bis;
		try {
			bis = new ObjectInputStream(new FileInputStream(file));
			setPO sp=(setPO)bis.readObject();
			bis.close();
			return sp;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			setPO sp=new setPO();
			return sp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	
	public static void main(String[] args){
		try {
			String nameString;
			nameString = pictureValidate("G:\\nju_beauty\\green.jpg");
			System.out.println(nameString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
