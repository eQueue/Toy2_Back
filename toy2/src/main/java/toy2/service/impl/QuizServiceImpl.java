package toy2.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import toy2.dao.QuizDao;
import toy2.dao.ScoreDao;
import toy2.dao.UserQuizDao;
import toy2.dto.QuizDto;
import toy2.dto.ScoreDto;
import toy2.dto.UserQuizDto;
import toy2.service.QuizService;
import toy2.service.security.CustomUserDetails;

@Service
public class QuizServiceImpl implements QuizService{
	@Autowired
	private QuizDao quizDao;
	@Autowired
	private UserQuizDao userQuizDao;
	@Autowired
	private ScoreDao scoreDao;
	
	
	
	@Override
	public List<QuizDto> getQuizzes() {
		
		List<QuizDto> quizzes = quizDao.getQuizzes();
		List<QuizDto> list = new ArrayList<QuizDto>();
		int randNum;
		Set<Integer> set =new HashSet<>();
		int size=quizzes.size();
		
		
		Random random = new Random();
		
		//5개의 중복없는 수 추출
		while(set.size()<5) {
			randNum=random.nextInt(size);
			set.add(randNum);
		}
		
		//추출된 수에 해당하는 index의 퀴즈를 quizzes에서 꺼내와 반환 list에 담기
		//5번 반복
		for(int i :set) {
			list.add(quizzes.get(i));
			System.out.println(i);
		}
		
		
		
		return list;
	}



	@Override
	public int insertUserQuiz(UserQuizDto[] userQuizList) {
		int success=0;
		
		if (userQuizDao.checkExistUserQuiz(userQuizList[0].getUserId())==null) { //등록된 퀴즈 없
			success = userQuizDao.insertUserQuiz(userQuizList);
		}
		
		return success;
		
		
	}



	@Override
	public List<QuizDto> getUserQuizzes(String nickname) {
		// TODO Auto-generated method stub
		List<QuizDto> list = quizDao.getUserQuizzes(nickname);
		
		return list;
	}



	@Override
	public int insertScore(ScoreDto scoreDto) {
		int success=scoreDao.insertScore(scoreDto);
		
		return success;
		
	}



	@Override
	public Boolean checkExistAnswerer(Long examinerId, Long answererId){
		ScoreDto scoreDto= scoreDao.checkAnswerer(examinerId, answererId);
		
		if(scoreDto==null) {//중복 없으면		
			return true;
		}else { 			//중복 있으면 false
			
			return false;
		}
		
		
	}



	@Override
	public List<ScoreDto> getScore(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
