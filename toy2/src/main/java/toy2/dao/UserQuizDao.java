package toy2.dao;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import toy2.dao.sql.QuizSqls;
import toy2.dto.ScoreDto;
import toy2.dto.UserQuizDto;

@Repository
public class UserQuizDao {
	private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<UserQuizDto> rowMapper = BeanPropertyRowMapper.newInstance(UserQuizDto.class);

    public UserQuizDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
        		.withTableName("User_Quiz");
    }
    
    public int insertUserQuiz(UserQuizDto[] userQuizList) {
    	
    	SqlParameterSource[] params = new SqlParameterSource[userQuizList.length];
		
		for(int i=0; i<userQuizList.length; i++) {
			params[i]=new BeanPropertySqlParameterSource(userQuizList[i]);
			
		}
		
		
		
		try {
			int success=insertAction.executeBatch(params).length;
			return success;
		}catch (DuplicateKeyException e) {
			// TODO Auto-generated catch block
			return 0;
		}

    }
    
    public List<UserQuizDto> checkExistUserQuiz(Long userId) {
    	try {
			Map<String, Object> params = new HashMap<>();
			params.put("userId", userId);
			
			return jdbc.query(QuizSqls.CHECK_EXIST_USER_QUIZ, params, rowMapper);
		}catch(EmptyResultDataAccessException e) {//해당 조건이 없을경우
			return null;
		}
    	
    }
}
