package toy2.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import toy2.dao.UserDao;
import toy2.dto.QuizDto;
import toy2.dto.ScoreDto;
import toy2.dto.UserQuizDto;
import toy2.service.QuizService;
import toy2.service.UserService;
import toy2.service.security.CustomUserDetails;
import toy2.dto.*;


@RestController
@Api(tags="Quiz API", description = "Quiz API (퀴즈조회, 퀴즈등록, 점수등록)")
@RequestMapping(path="/quizzes")
public class Toy2ControllerApi {
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private UserDao userDao;
	
	//퀴즈조회
	@ApiOperation(value="퀴즈 랜덤 조회")
	@GetMapping
	public Map<String,Object> getQuizzes(){
		List<QuizDto> quizList= quizService.getQuizzes();
		


		Map<String, Object> map = new HashMap<>();
		map.put("quizList", quizList);
		
		
		return map;
		
	}
	
	//유저의 퀴즈 조회
	@ApiOperation(value="유저에 등록된 퀴즈 조회")
	@GetMapping(path="/{nickname}")
	public Map<String,Object> getUserQuiz(@PathVariable (name = "nickname") String nickname){
		
				
		List<QuizDto> quizList= quizService.getUserQuizzes(nickname);
		
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("quizList", quizList);
		
		return map;
		
	}
	
	//유저가 만든 퀴즈 등록
	@ApiOperation(value="유저의 퀴즈 등록")
	@PostMapping
	public Map<String,Object> insertUserQuiz(@RequestBody Map<String, Object> requestParam, @AuthenticationPrincipal CustomUserDetails user) {
		
		int success=0;
		Map<String, Object> map = new HashMap<>();
		//UserQuizDto userQuiz=new UserQuizDto();
		String nickname=(String) requestParam.get("nickname");
		Long userId=userDao.findByNickName(nickname).getUserId();
		
		
		List<Map<String, Object>> requestUserQuizList=(List<Map<String, Object>>)requestParam.get("quizList");
		int len=requestUserQuizList.size();
		UserQuizDto[] userQuizList=new UserQuizDto[len];
		
		
		for (int i=0; i<len; i++) {
			Long quizId=Long.valueOf((int)requestUserQuizList.get(i).get("id"));
			int answer=(int) requestUserQuizList.get(i).get("answer");
			userQuizList[i]=new UserQuizDto();
			
			userQuizList[i].setQuizId(quizId);
			userQuizList[i].setUserId(userId);
			userQuizList[i].setAnswer(answer);
			
			
		}
		
		success=quizService.insertUserQuiz(userQuizList);
		System.out.println(success);
		
		if(success==0) {
			System.out.println("User Quiz insert fail");
			
			map.put("success", "false");
		}
		else {
			System.out.println("User Quiz insert success");
			map.put("success", "true");
		}
		
		return map;
				
	}
	
	
	//Score 등록
	@ApiOperation(value="점수 등록")
	@PostMapping(path="/{nickname}")
	public Map<String, Object> insertScore(@RequestBody Map<String, Object> requestParam, 
			@PathVariable (name = "nickname") String nickname,
			@AuthenticationPrincipal CustomUserDetails user) {
//		{"answerer": "answerer_nickname",
//			"score":"40"}
		int flag;
		Map<String, Object> map = new HashMap<>();
		
		String examiner=nickname;
		String answerer=(String) requestParam.get("answerer");
		String score=(String) requestParam.get("score");
		
		ScoreDto scoreDto=new ScoreDto();
		Long answererId=userDao.findByNickName(answerer).getUserId();
		Long examinerId=userDao.findByNickName(nickname).getUserId();
		if (quizService.checkExistAnswerer(examinerId,answererId)) { //중복없으면
			scoreDto.setAnswerer(answererId);
			scoreDto.setExaminer(userDao.findByNickName(examiner).getUserId());
			scoreDto.setScore(Integer.parseInt(score));
			
			flag=quizService.insertScore(scoreDto);
			
			
			if(flag==0) {
				System.out.println("Score insert fail");
				map.put("success", null);
				return map;
			}
			else {
				System.out.println("Score insert success");
				map.put("success","true");
				return map;
			}
			
		}else {//중복있으면
			map.put("success","false");
			return map;
		}				
		
		
	}
	

}
