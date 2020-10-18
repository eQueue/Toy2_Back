package toy2.dto;

public class UserQuizDto {
	private Long quizId;
	private Long userId;
	private int answer;
	
	
	
	@Override
	public String toString() {
		return "UserQuizDto [quizID=" + quizId + ", userID=" + userId + ", answer=" + answer + "]";
	}



	public Long getQuizId() {
		return quizId;
	}



	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}



	public long getUserId() {
		return userId;
	}



	public void setUserId(long userId) {
		this.userId = userId;
	}



	public int getAnswer() {
		return answer;
	}



	public void setAnswer(int answer) {
		this.answer = answer;
	}

	
	
}
