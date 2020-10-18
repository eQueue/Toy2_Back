package toy2.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import toy2.dto.*;

public interface QuizService {
	
	
	
	public List<QuizDto> getQuizzes();
	public List<QuizDto> getUserQuizzes(String nickname);
	public List<ScoreDto> getScore(Long userId);
	public int insertScore(ScoreDto scoreDto);
	public Boolean checkExistAnswerer(Long examinerId, Long answererId);
	public int insertUserQuiz(UserQuizDto[] userQuizList);
	
}
