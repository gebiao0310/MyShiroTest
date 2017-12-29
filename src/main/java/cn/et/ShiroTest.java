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
		//获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager  
		Factory<org.apache.shiro.mgt.SecurityManager> factory= new IniSecurityManagerFactory("classpath:Shiro.ini"); 
		//得到SecurityManager实例 并绑定给SecurityUtils  
		org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager); 
		//获取当前用户
		Subject currentUser = SecurityUtils.getSubject();
		//当前用户的会话
		Session session = currentUser.getSession();
		//判断是否认证    未认证需登录
		/**
		 * 用户包括两部分   principals   and    credentials
		 * principals(本人)表示用户的标识信息   如：用户名，用户地址等
		 * credentials(凭证)表示用户用于登录的凭证 如：密码，证书等
		 */
		if ( !currentUser.isAuthenticated() ) {
		    UsernamePasswordToken token = new UsernamePasswordToken("biaoge", "123456");
		    //记住密码
		    token.setRememberMe(true);
		    try {
		    	currentUser.login(token);
		        System.out.println("登陆成功");
		        System.out.println(currentUser.isAuthenticated());
		        System.out.println(currentUser.isRemembered());
		        //判断是否拥有角色角色
		        if(currentUser.hasRole("role1")){
		        	System.out.println("拥有rolel角色");
		        }
		        //判断权限
		        if(currentUser.isPermitted("user:delete:1")){
		        	System.out.println("拥有user:delete:1权限");
		        }
		        //if no exception, that's it, we're done!
		    } catch ( UnknownAccountException uae ) {
		        System.out.println("账号错误");
		    } catch ( IncorrectCredentialsException ice ) {
		    	System.out.println("账号密码不匹配");
		    } catch ( LockedAccountException lae ) {
		    	System.out.println("账号锁定");
		    } catch ( AuthenticationException ae ) {
		    	System.out.println("未知异常");
		    }
		}
	}
}
