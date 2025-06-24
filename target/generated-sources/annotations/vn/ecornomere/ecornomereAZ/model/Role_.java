package vn.ecornomere.ecornomereAZ.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Role.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Role_ {

	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Role#name
	 **/
	public static volatile SingularAttribute<Role, String> name;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Role#description
	 **/
	public static volatile SingularAttribute<Role, String> description;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Role#id
	 **/
	public static volatile SingularAttribute<Role, Long> id;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Role
	 **/
	public static volatile EntityType<Role> class_;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Role#users
	 **/
	public static volatile ListAttribute<Role, User> users;

	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String USERS = "users";

}

