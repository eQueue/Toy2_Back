package toy2.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import toy2.dao.UserDao;
import toy2.service.UserService;

@RestController
@Api(tags="User API", description = "User API (ID중복검사, 회원가입, 랭킹조회)")
@RequestMapping("/users")
public class UserController {
    // 스프링 컨테이너가 생성자를 통해 자동으로 주입한다.


	@Autowired
	UserService userService;
	@Autowired
	UserDao userdao;
	
	Logger log= LoggerFactory.getLogger(LoginController.class);
	
	@ApiOperation(value="중복검사")
	@PostMapping //nickname 중복검사
	public Map<String,String> checkNickName(@RequestBody Map<String, String> json){
		String nickname = json.get("nickname");
		Map<String, String> map = userService.checkNickName(nickname);
	
		return map;
	}
	
	@ApiOperation(value="회원가입")
	@PostMapping("/signup") //회원가입
	public Map<String,String> signUp(@RequestBody Map<String, String> json){
		String nickname = json.get("nickname");
		String password = json.get("password");
		Map<String, String> map = userService.signUp(nickname, password);
	
		return map;
	}
	
	@ApiOperation(value="랭킹조회")
	@PostMapping(path="/rank")//랭킹 조회
	   public Map<String, Object> searchRanking(@RequestBody Map<String, String> json){
		  String nickname = json.get("nickname");
		  Long num = userdao.findByNickName(nickname).getUserId();
	      Map<String, Object> map = new HashMap<String, Object>();
	      map.put("ranking", userService.searchUserRank(num));
	      return map;
	}

}
