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

import com.robowebi.model.Message;

/**
 * Backing bean for Message entities.
 * <p/>
 * This class provides CRUD functionality for all Message entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD
 * framework or custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class MessageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Message entities
	 */

	private Long id;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Message message;

	public Message getMessage() {
		return this.message;
	}

	public void setMessage(Message message) {
		this.message = message;
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
			this.message = this.example;
		} else {
			this.message = findById(getId());
		}
	}

	public Message findById(Long id) {

		return this.entityManager.find(Message.class, id);
	}

	/*
	 * Support updating and deleting Message entities
	 */

	public String update() {
		this.conversation.end();

		try {
			if (this.id == null) {
				this.entityManager.persist(this.message);
				return "search?faces-redirect=true";
			} else {
				this.entityManager.merge(this.message);
				return "view?faces-redirect=true&id=" + this.message.getId();
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
			Message deletableEntity = findById(getId());

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
	 * Support searching Message entities with pagination
	 */

	private int page;
	private long count;
	private List<Message> pageItems;

	private Message example = new Message();

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return 10;
	}

	public Message getExample() {
		return this.example;
	}

	public void setExample(Message example) {
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
		Root<Message> root = countCriteria.from(Message.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<Message> criteria = builder.createQuery(Message.class);
		root = criteria.from(Message.class);
		TypedQuery<Message> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<Message> root) {

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String to = this.example.getTo();
		if (to != null && !"".equals(to)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("to")),
					'%' + to.toLowerCase() + '%'));
		}
		String receivedFrom = this.example.getReceivedFrom();
		if (receivedFrom != null && !"".equals(receivedFrom)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("receivedFrom")),
					'%' + receivedFrom.toLowerCase() + '%'));
		}
		String body = this.example.getBody();
		if (body != null && !"".equals(body)) {
			predicatesList.add(builder.like(
					builder.lower(root.<String> get("body")),
					'%' + body.toLowerCase() + '%'));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<Message> getPageItems() {
		return this.pageItems;
	}

	public long getCount() {
		return this.count;
	}

	/*
	 * Support listing and POSTing back Message entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Message> getAll() {

		CriteriaQuery<Message> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Message.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(Message.class))).getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter() {

		final MessageBean ejbProxy = this.sessionContext
				.getBusinessObject(MessageBean.class);

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

				return String.valueOf(((Message) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private Message add = new Message();

	public Message getAdd() {
		return this.add;
	}

	public Message getAdded() {
		Message added = this.add;
		this.add = new Message();
		return added;
	}
}
