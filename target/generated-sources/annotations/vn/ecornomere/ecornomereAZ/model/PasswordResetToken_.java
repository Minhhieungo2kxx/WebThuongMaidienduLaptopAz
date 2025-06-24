package vn.ecornomere.ecornomereAZ.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(PasswordResetToken.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class PasswordResetToken_ {

	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.PasswordResetToken#expiryDate
	 **/
	public static volatile SingularAttribute<PasswordResetToken, LocalDateTime> expiryDate;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.PasswordResetToken#id
	 **/
	public static volatile SingularAttribute<PasswordResetToken, Long> id;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.PasswordResetToken
	 **/
	public static volatile EntityType<PasswordResetToken> class_;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.PasswordResetToken#user
	 **/
	public static volatile SingularAttribute<PasswordResetToken, User> user;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.PasswordResetToken#token
	 **/
	public static volatile SingularAttribute<PasswordResetToken, String> token;

	public static final String EXPIRY_DATE = "expiryDate";
	public static final String ID = "id";
	public static final String USER = "user";
	public static final String TOKEN = "token";

}

