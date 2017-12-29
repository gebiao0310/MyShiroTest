package cn.et;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class ShiroTest {
	public static void main(String[] args) {
		//��ȡSecurityManager�������˴�ʹ��Ini�����ļ���ʼ��SecurityManager  
		Factory<org.apache.shiro.mgt.SecurityManager> factory= new IniSecurityManagerFactory("classpath:Shiro.ini"); 
		//�õ�SecurityManagerʵ�� ���󶨸�SecurityUtils  
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager); 
		//��ȡ��ǰ�û�
		Subject currentUser = SecurityUtils.getSubject();
		//��ǰ�û��ĻỰ
		Session session = currentUser.getSession();
		//�ж��Ƿ���֤    δ��֤���¼
		/**
		 * �û�����������   principals   and    credentials
		 * principals(����)��ʾ�û��ı�ʶ��Ϣ   �磺�û������û���ַ��
		 * credentials(ƾ֤)��ʾ�û����ڵ�¼��ƾ֤ �磺���룬֤���
		 */
		if ( !currentUser.isAuthenticated() ) {
		    UsernamePasswordToken token = new UsernamePasswordToken("biaoge", "123456");
		    //��ס����
		    token.setRememberMe(true);
		    try {
		    	currentUser.login(token);
		        System.out.println("��½�ɹ�");
		        System.out.println(currentUser.isAuthenticated());
		        System.out.println(currentUser.isRemembered());
		        //�ж��Ƿ�ӵ�н�ɫ��ɫ
		        if(currentUser.hasRole("role1")){
		        	System.out.println("ӵ��rolel��ɫ");
		        }
		        //�ж�Ȩ��
		        if(currentUser.isPermitted("user:delete:1")){
		        	System.out.println("ӵ��user:delete:1Ȩ��");
		        }
		        //if no exception, that's it, we're done!
		    } catch ( UnknownAccountException uae ) {
		        System.out.println("�˺Ŵ���");
		    } catch ( IncorrectCredentialsException ice ) {
		    	System.out.println("�˺����벻ƥ��");
		    } catch ( LockedAccountException lae ) {
		    	System.out.println("�˺�����");
		    } catch ( AuthenticationException ae ) {
		    	System.out.println("δ֪�쳣");
		    }
		}
	}
}
