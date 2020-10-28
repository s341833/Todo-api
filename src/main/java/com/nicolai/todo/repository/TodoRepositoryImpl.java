package com.nicolai.todo.repository;

import com.nicolai.todo.domain.Todo;
import com.nicolai.todo.exceptions.TodoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class TodoRepositoryImpl implements TodoRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT_TODO = "select id, todo, frist, done, username from todo where username";
    private static final String SQL_INSERT_TODO = "insert into todo(todo, frist, done, username) values(?, ?, ?, ?)";

    @Override
    public List<Todo> getTodos(String username) {
        String sql = SQL_SELECT_TODO + " = '" + username + "'";
        List<Todo> mapList = jdbcTemplate.query(sql, todoRowMapper);

        return mapList;
    }

    @Override
    public int addTodo(String username, String todo, Date date, boolean done) throws TodoException{

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps =
                        connection.prepareStatement(SQL_INSERT_TODO,
                                Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, todo);
                ps.setDate(2, date);
                ps.setBoolean(3, done);
                ps.setString(4, username);
                return ps;
            }, keyHolder);

            return jdbcTemplate.queryForObject("select max(id) from todo", Integer.class);
        } catch (Exception e) {
            System.out.println(e);
            throw new TodoException("Kunne ikke opprette todo");
        }

    }

    private RowMapper<Todo> todoRowMapper = ((rs, rowNum) -> {
        return new Todo(rs.getInt("id"), rs.getString("todo"),
                rs.getDate("frist"), rs.getBoolean("done"),
                rs.getString("username"));
    });
}
