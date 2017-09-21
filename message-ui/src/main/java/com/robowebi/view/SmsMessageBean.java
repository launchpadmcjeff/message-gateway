package com.robowebi.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.robowebi.model.SmsMessage;

/**
 * Backing bean for SmsMessage entities.
 * <p/>
 * This class provides CRUD functionality for all SmsMessage entities. It
 * focuses purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt>
 * for state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class SmsMessageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving SmsMessage entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private SmsMessage smsMessage;

	public SmsMessage getSmsMessage() {
		return this.smsMessage;
	}

	public void setSmsMessage(SmsMessage smsMessage) {
		this.smsMessage = smsMessage;
	}

	@Inject
	private Conversation conversation;

	@PersistenceContext(unitName = "message-ui-persistence-unit", type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	public String create() {

		this.conversation.begin();
		this.conversation.setTimeout(1800000L);
		return "create?faces-redirect=true";
	}

	public void retrieve() {

		if (FacesContext.getCurrentInstance().isPostback()) {
			return;
		}

		if (this.conversation.isTransient()) {
			this.conversation.begin();
			this.conversation.setTimeout(1800000L);
		}

		if (this.id == null) {
			this.smsMessage = this.example;
		} else {
			this.smsMessage = findById(getId());
		}
	}

	public SmsMessage findById(Long id) {

		return this.entityManager.find(SmsMessage.class, id);
	}

	/*
	 * Support updating and deleting SmsMessage entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.smsMessage);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.smsMessage);
				return "view?faces-redirect=true&id=" + this.smsMessage.getId();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			return null;
		}
	}

	public String delete() {
		this.conversation.end();

		try {
			SmsMessage deletableEntity = findById(getId());

			this.entityManager.remove(deletableEntity);
			this.entityManager.flush();
			return "search?faces-redirect=true";
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(e.getMessage()));
			return null;
		}
	}

	/*
	 * Support searching SmsMessage entities with pagination
	 */

	private int page;
	private long count;
	private List<SmsMessage> pageItems;

	private SmsMessage example = new SmsMessage();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public SmsMessage getExample() {
		return this.example;
	}

	public void setExample(SmsMessage example) {
		this.example = example;
	}

	public String search() {
		this.page = 0;
		return null;
	}

	public void paginate() {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		// Populate this.count

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<SmsMessage> root = countCriteria.from(SmsMessage.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<SmsMessage> criteria = builder
				.createQuery(SmsMessage.class);
		root = criteria.from(SmsMessage.class);
		TypedQuery<SmsMessage> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<SmsMessage> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String toPhoneCountryCode = this.example.getToPhoneCountryCode();
		if (toPhoneCountryCode != null && !"".equals(toPhoneCountryCode)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("toPhoneCountryCode")),
					'%' + toPhoneCountryCode.toLowerCase() + '%'));
		}
		String toPhoneAreaCode = this.example.getToPhoneAreaCode();
		if (toPhoneAreaCode != null && !"".equals(toPhoneAreaCode)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("toPhoneAreaCode")),
					'%' + toPhoneAreaCode.toLowerCase() + '%'));
		}
		String toPhonePrefix = this.example.getToPhonePrefix();
		if (toPhonePrefix != null && !"".equals(toPhonePrefix)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("toPhonePrefix")),
					'%' + toPhonePrefix.toLowerCase() + '%'));
		}
		String toPhoneLine = this.example.getToPhoneLine();
		if (toPhoneLine != null && !"".equals(toPhoneLine)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("toPhoneLine")),
					'%' + toPhoneLine.toLowerCase() + '%'));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<SmsMessage> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back SmsMessage entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<SmsMessage> getAll() {

		CriteriaQuery<SmsMessage> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(SmsMessage.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(SmsMessage.class)))
				.getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final SmsMessageBean ejbProxy = this.sessionContext
				.getBusinessObject(SmsMessageBean.class);

		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {

				return ejbProxy.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {

				if (value == null) {
					return "";
				}

				return String.valueOf(((SmsMessage) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private SmsMessage add = new SmsMessage();

	public SmsMessage getAdd() {
		return this.add;
	}

	public SmsMessage getAdded() {
		SmsMessage added = this.add;
		this.add = new SmsMessage();
		return added;
	}
}
