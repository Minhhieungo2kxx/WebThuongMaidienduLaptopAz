package vn.ecornomere.ecornomereAZ.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Order.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Order_ {

	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Order#totalPrice
	 **/
	public static volatile SingularAttribute<Order, Double> totalPrice;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Order#receiverName
	 **/
	public static volatile SingularAttribute<Order, String> receiverName;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Order#receiverAddress
	 **/
	public static volatile SingularAttribute<Order, String> receiverAddress;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Order#orderDetails
	 **/
	public static volatile ListAttribute<Order, OrderDetail> orderDetails;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Order#receiverPhone
	 **/
	public static volatile SingularAttribute<Order, String> receiverPhone;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Order#paymentMethod
	 **/
	public static volatile SingularAttribute<Order, String> paymentMethod;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Order#id
	 **/
	public static volatile SingularAttribute<Order, Long> id;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Order#paymentTime
	 **/
	public static volatile SingularAttribute<Order, String> paymentTime;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Order
	 **/
	public static volatile EntityType<Order> class_;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Order#totalPriceaddShip
	 **/
	public static volatile SingularAttribute<Order, Double> totalPriceaddShip;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Order#user
	 **/
	public static volatile SingularAttribute<Order, User> user;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Order#paymentStatus
	 **/
	public static volatile SingularAttribute<Order, String> paymentStatus;
	
	/**
	 * @see vn.ecornomere.ecornomereAZ.model.Order#status
	 **/
	public static volatile SingularAttribute<Order, String> status;

	public static final String TOTAL_PRICE = "totalPrice";
	public static final String RECEIVER_NAME = "receiverName";
	public static final String RECEIVER_ADDRESS = "receiverAddress";
	public static final String ORDER_DETAILS = "orderDetails";
	public static final String RECEIVER_PHONE = "receiverPhone";
	public static final String PAYMENT_METHOD = "paymentMethod";
	public static final String ID = "id";
	public static final String PAYMENT_TIME = "paymentTime";
	public static final String TOTAL_PRICEADD_SHIP = "totalPriceaddShip";
	public static final String USER = "user";
	public static final String PAYMENT_STATUS = "paymentStatus";
	public static final String STATUS = "status";

}

