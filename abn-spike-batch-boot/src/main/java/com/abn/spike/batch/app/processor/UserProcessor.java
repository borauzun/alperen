package com.abn.spike.batch.app.processor;
import org.springframework.batch.item.ItemProcessor;
import com.abn.spike.batch.app.model.User;
public class UserProcessor implements ItemProcessor<User, User> {

	public User process(User item) throws Exception {
		// TODO Auto-generated method stub
		return item;
	}
   
}

