package com.abn.spike.batch.app.processor;
import org.springframework.batch.item.ItemProcessor;
import com.abn.spike.batch.app.model.User;
import com.abn.spike.batch.app.model.User2;
public class UserProcessor implements ItemProcessor<User2, User2> {

	public User2 process(User2 item) throws Exception {
		// TODO Auto-generated method stub
		return item;
	}
   
}

