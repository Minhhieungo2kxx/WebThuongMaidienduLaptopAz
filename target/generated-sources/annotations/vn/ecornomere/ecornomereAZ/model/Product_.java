package vn.ecornomere.ecornomereAZ.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Product.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Product_ {

	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Product#image
	 **/
	public static volatile SingularAttribute<Product, String> image;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Product#sold
	 **/
	public static volatile SingularAttribute<Product, Long> sold;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Product#factory
	 **/
	public static volatile SingularAttribute<Product, String> factory;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Product#quantity
	 **/
	public static volatile SingularAttribute<Product, Long> quantity;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Product#target
	 **/
	public static volatile SingularAttribute<Product, String> target;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Product#orderDetails
	 **/
	public static volatile ListAttribute<Product, OrderDetail> orderDetails;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Product#detailDesc
	 **/
	public static volatile SingularAttribute<Product, String> detailDesc;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Product#price
	 **/
	public static volatile SingularAttribute<Product, Double> price;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Product#name
	 **/
	public static volatile SingularAttribute<Product, String> name;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Product#cartDetails
	 **/
	public static volatile ListAttribute<Product, CartDetail> cartDetails;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Product#shortDesc
	 **/
	public static volatile SingularAttribute<Product, String> shortDesc;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Product#id
	 **/
	public static volatile SingularAttribute<Product, Long> id;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Product
	 **/
	public static volatile EntityType<Product> class_;

	public static final String IMAGE = "image";
	public static final String SOLD = "sold";
	public static final String FACTORY = "factory";
	public static final String QUANTITY = "quantity";
	public static final String TARGET = "target";
	public static final String ORDER_DETAILS = "orderDetails";
	public static final String DETAIL_DESC = "detailDesc";
	public static final String PRICE = "price";
	public static final String NAME = "name";
	public static final String CART_DETAILS = "cartDetails";
	public static final String SHORT_DESC = "shortDesc";
	public static final String ID = "id";

}

