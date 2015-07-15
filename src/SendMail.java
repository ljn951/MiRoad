import java.util.*; 

import javax.mail.*; 
import javax.mail.internet.*; 
import javax.swing.JOptionPane;
import javax.activation.*;

public class SendMail {

              private MimeMessage mimeMsg; //MIME�ʼ�����

              private Session session; //�ʼ��Ự���� 
              private Properties props; //ϵͳ���� 
              private static boolean needAuth = true; //smtp�Ƿ���Ҫ��֤
              private String username = ""; //smtp��֤�û��������� 
              private String passWord = "";
              private Multipart mp; //Multipart����,�ʼ�����,����,���������ݾ���ӵ����к�������MimeMessage����
              private MiMail mm = null;

/**
 * Ĭ�Ϲ������������ṩ�ʼ�����������
 */
         public SendMail(MiMail mm) { 
        	 this.mm = mm;
                 setSmtpHost("smtp.163.com");//����û��ָ���ʼ�������,�ʹ�getConfig���л�ȡ 
                 createMimeMessage(); 
              } 
/**
 *  ���ι��������ṩ�ʼ�������
 * @param smtp smtp��������ַ
 */
         public SendMail(String smtp){
                setSmtpHost(smtp); 
                createMimeMessage(); 
              }

 

/** 
 * ����ϵͳ���Զ���
* @param hostName String 
*/ 
         public void setSmtpHost(String hostName) { 
                    //System.out.println("����ϵͳ���ԣ�mail.smtp.host = "+hostName); 
                    if(props == null)props = System.getProperties(); //���ϵͳ���Զ���

                    props.put("mail.smtp.host",hostName); //����SMTP���� 
}


/** 
 * ����mime�ʼ�����
* @return boolean �����ɹ�����true
*/ 
        public boolean createMimeMessage() { 
                      try{ 
                            //System.out.println("Ԥ����ȡ�ʼ��Ự����"); 
                            session = Session.getDefaultInstance(props,null); //����ʼ��Ự���� 
                        }catch(Exception e){ 
                            //System.err.println("��ȡ�ʼ��Ự����ʱ��������"+e); 
                            return false; 
                       }

                      //System.out.println("Ԥ������MIME�ʼ�����"); 
                      try{ 
                      mimeMsg = new MimeMessage(session); //����MIME�ʼ����� 
                      mp = new MimeMultipart();

                      return true; 
                      }catch(Exception e){ 
                                // System.err.println("����MIME�ʼ�����ʧ�ܣ�"+e); 
                                 return false; 
                } 
         }

 

/**
 * ����smtp�������֤�����������ϵͳ���� 
* @param need boolean �Ƿ���Ҫ���������֤
*/ 
             public void setNeedAuth(boolean need) { 
                         //System.out.println("����smtp�����֤��mail.smtp.auth = "+need); 
                         if(props == null)props = System.getProperties();

                         if(need){ 
                                  props.put("mail.smtp.auth","true"); 
                           }else{ 
                                  props.put("mail.smtp.auth","false"); 
                 } 
         } 
/** 
 * ����smtp��¼���û�������
* @param name String �û���
* @param pass String ����
*/ 
           public void setNamePass(String name,String pass) { 
                         username = name; 
                         passWord = pass; 
                  } 
/** 
 * �����ʼ�����
* @param mailSubject String �ʼ�����
* @return boolean ���óɹ�������true�����򷵻�false
*/ 
              public boolean setSubject(String mailSubject) { 
                   //System.out.println("�����ʼ����⣡");
                   boolean b=false;
                   try{ 
                         mimeMsg.setSubject(mailSubject); 
                         b=true; 
                      }catch(Exception e) { 
                         //System.err.println("�����ʼ����ⷢ������"); 
                         b=false;
                   }
                   return b;
                  } 
 /** 
  * �����ʼ���������
 * @param mailBody String �ʼ�����
 * @return ���óɹ�����true�����򷵻�false
 */ 
             public boolean setBody(String mailBody) { 
                 try{ 
                        BodyPart bp = new MimeBodyPart(); 
                        bp.setContent(""+mailBody,"text/html;charset=GB2312"); 
                        mp.addBodyPart(bp);

                        return true; 
                  }catch(Exception e){ 
                         //System.err.println("�����ʼ�����ʱ��������"+e); 
                         return false; 
                  } 
          } 
/**
 * �����ʼ���������
 * @param filename �������ļ���
 * @return �ɹ�����true�����򷵻�false
 */
          public boolean addFileAffix(String filename) {

                  //System.out.println("�����ʼ�������"+filename); 
                  try{ 
                        BodyPart bp = new MimeBodyPart(); 
                        FileDataSource fileds = new FileDataSource(filename); 
                        bp.setDataHandler(new DataHandler(fileds)); 
                        bp.setFileName(fileds.getName());

                        mp.addBodyPart(bp);

                        return true; 
                     }catch(Exception e){ 
                 // System.err.println("�����ʼ�������"+filename+"��������"+e); 
                  return false; 
          } 
       } 
/**
 * ���÷�����
 * @param from �����������ַ
 * @return ���óɹ�����ture�����򷵻�false
 */
         public boolean setFrom(String from) { 
                  //System.out.println("���÷����ˣ�"); 
                  try{ 
                         mimeMsg.setFrom(new InternetAddress(from)); //���÷����� 
                         return true; 
                    } catch(Exception e) {
                     //System.out.println("���÷���������ʧ��");
                     e.printStackTrace();
                     return false; 
                     } 
               }
/**
 * �����ռ��������ַ
 * @param to �ռ�������
 * @return �����ɹ�����true�����򷵻�false
 */
 public boolean setTo(String to){ 
         if(to == null)return false; 
        try{ 
                    mimeMsg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to)); 
                    return true; 
           }catch(Exception e){
           // System.out.println("�����ռ��������ַʧ�ܡ�");
            e.printStackTrace();
            return false;
            } 
       }
/**
 * ת���ʼ�
 * @param copyto ת���ʼ����ռ��˵�ַ�������Ƕ��
 * @return ת���ɹ�������ture�����򷵻�false
 */
         public boolean setCopyTo(String copyto) { 
                 if(copyto == null)return false; 
                 try{ 
                       mimeMsg.setRecipients(Message.RecipientType.CC,(Address[])InternetAddress.parse(copyto)); 
                       return true; 
                }catch(Exception e){
                 return false;
                 } 
          }
 
/**
 * �����ʼ�
 * @return �ɹ�����ture�����򷵻�false
 */
          public boolean sendout(){ 
                    try{ 
                          mimeMsg.setContent(mp); 
                          mimeMsg.saveChanges(); 
                         // System.out.println("���ڷ����ʼ�....");

                          Session mailSession = Session.getInstance(props,null); 
                          Transport transport = mailSession.getTransport("smtp"); 
                          transport.connect((String)props.get("mail.smtp.host"),username,passWord); 
                          transport.sendMessage(mimeMsg,mimeMsg.getRecipients(Message.RecipientType.TO)); 
                          //transport.send(mimeMsg);

                         // System.out.println("�����ʼ��ɹ���"); 
                          Tips("�����ʼ��ɹ���");
                          transport.close();

                          return true; 
                     }catch(Exception e){ 
                    	 Tips("�ʼ�����ʧ�ܣ�");
                         // System.err.println("�ʼ�����ʧ�ܣ�"+e); 
                          return false; 
                     } 
              } 
/**
 * �������
 * @param args
 */

          //��ʾ��ʾ
          public void Tips(String tips){
  			JOptionPane.showMessageDialog(mm.panel, tips,
  					"MiRoad", JOptionPane.WARNING_MESSAGE);
  	}
          
          
}