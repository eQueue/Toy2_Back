package toy2.dao.sql;

public class QuizSqls {
	public static final String SELECT_QUIZZES="select * from Quiz;";
	public static final String SELECT_USER_QUIZZES="select uq.quiz_id, content, answer "
			+ "from Quiz as q, User_Quiz as uq, User as u "
			+ "where q.quiz_id=uq.quiz_id and uq.user_id= u.user_id and u.nickname= :nickname;";
	public static final String CHECK_ANSWERER="select * from Score where examiner = :examiner and answerer = :answerer";
	public static final String INSERT_SCORE="insert into Score values(:examiner, :answerer, :score)";
	public static final String INSERT_USERQUIZ="insert into User_Quiz values(:quizId, :userId, :answer)";
	public static final String CHECK_EXIST_USER_QUIZ="select * from User_Quiz where user_id= :userId";

}
