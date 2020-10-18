package toy2.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import toy2.dao.sql.QuizSqls;
import toy2.dao.sql.UserDaoSqls;
import toy2.dto.QuizDto;
import toy2.dto.ScoreDto;
import toy2.service.security.CustomUserDetails;



@Repository
public class ScoreDao {
	private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private RowMapper<ScoreDto> rowMapper = BeanPropertyRowMapper.newInstance(ScoreDto.class);

    public ScoreDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
        		.withTableName("Score");
    }
    
    public int insertScore(ScoreDto scoreDto) {
    	
			Map<String, Object> params = new HashMap<>();
			params.put("examiner", scoreDto.getExaminer());
			params.put("answerer", scoreDto.getAnswerer());
			params.put("score", scoreDto.getScore());
			return jdbc.update(QuizSqls.INSERT_SCORE, params);
    }
    
    public ScoreDto checkAnswerer(Long examinerId, Long answererId) {
		try {
			Map<String, Object> params = new HashMap<>();
			params.put("examiner", examinerId);
			params.put("answerer", answererId);
			return jdbc.queryForObject(QuizSqls.CHECK_ANSWERER, params, rowMapper);
		}catch(EmptyResultDataAccessException e) {//해당 조건이 없을경우
			return null;
		}
	}
	
	
	
	
}
