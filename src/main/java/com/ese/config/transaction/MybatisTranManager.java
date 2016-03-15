package com.ese.config.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @File Name : MybatisTranManager.java
 * @Project Name : DMS
 * @pakage Name : com.ese.config.transaction
 * @create Date : 2015. 9. 9.
 * @explain : 마이바티스 전용 트렌젝션 메니저 
 * @made by : GOEDOKID
 */
@Service
@Scope("prototype")
public class MybatisTranManager extends DefaultTransactionDefinition {
	private static final long serialVersionUID = -1375151959664915520L;
	
	@Autowired
	@Qualifier("jpaTx")
	PlatformTransactionManager transactionManager;
	
	TransactionStatus status;

	/**
	 * @Method Name : start
	 * @create Date : 2015. 9. 9.
	 * @made by : GOEDOKID
	 * @explain : 트렌젝션 시작 
	 * @param : 
	 * @return : void
	 */
	public void start() throws TransactionException {
		status = transactionManager.getTransaction(this);
	}

	/**
	 * @Method Name : commit
	 * @create Date : 2015. 9. 9.
	 * @made by : GOEDOKID
	 * @explain : 커밋 
	 * @param : 
	 * @return : void
	 */
	public void commit() throws TransactionException {
		if (!status.isCompleted()) {
			transactionManager.commit(status);
		}
	}

	/**
	 * @Method Name : rollback
	 * @create Date : 2015. 9. 9.
	 * @made by : GOEDOKID
	 * @explain : 롤백
	 * @param : 
	 * @return : void
	 */
	public void rollback() throws TransactionException {
		if (!status.isCompleted()) {
			transactionManager.rollback(status);
		}
	}

	/**
	 * @Method Name : end
	 * @create Date : 2015. 9. 9.
	 * @made by : GOEDOKID
	 * @explain : 롤백
	 * @param : 
	 * @return : void
	 */
	public void end() throws TransactionException {
		rollback();
	}
}