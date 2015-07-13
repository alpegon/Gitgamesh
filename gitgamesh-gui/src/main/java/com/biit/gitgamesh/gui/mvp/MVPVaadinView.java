package com.biit.gitgamesh.gui.mvp;

import javax.annotation.PostConstruct;

import com.biit.gitgamesh.utils.IActivity;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

/**
 * This class holds the relationship between a view and a presenter and combines
 * them to create a full webpage. In vaadin we are showing directly webpages. If
 * we inject webpages as beans then we need to make the initialization of
 * presenter known to the view thus breaking any generic separation. That's why
 * this implementation of MVP has a {@code webpage glue} that holds the
 * relationship betwween a presenter and a view and can be used to define
 * correctly a webpage to use in vaadin navigator or injected through
 * spring-vaadin while maintaining any specific functionality not related to the
 * view in the presenter.
 * 
 * This webpage definition includes a generic debug id and css class name based
 * on the class name.
 *
 * @param <IV>
 * @param <IP>
 */
public abstract class MVPVaadinView<IV extends IMVPView<IP>, IP extends IMVPPresenter<IV>> extends CustomComponent implements View {
	private static final long serialVersionUID = -1595123209492220479L;
	private static final char CLASS_INJECTOR_NAME = '$';
	private static final char CLASS_INSTANCE_NAME = '@';
	private static final String BIIT_STYLE_PREFIX = "biit";

	private IV view;
	private IP presenter;
	private boolean initialized;

	public MVPVaadinView() {
		initialized = false;
		setId(getNotInjectedName());
		setStyleName(getDefaultStyleName());
		setSizeFull();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		getView().enter(event);
	}

	/**
	 * In injected enviroments this method will be called after constructor
	 * method. Otherwise the user has to call it manually before using the view
	 * or include it on the constructor.
	 */
	@PostConstruct
	public void init() {
		setView(instanciateView());
		setPresenter(instanciatePresenter());
		setCompositionRoot(getView().getCompositionRoot());

		getView().setPresenter(getPresenter());
		getPresenter().setView(getView());

		getPresenter().init();
		getView().init();

		initialized = true;
	}

	private void setPresenter(IP presenter) {
		this.presenter = presenter;
	}

	private void setView(IV view) {
		this.view = view;
	}

	private IMVPView<IP> getView() {
		return view;
	}

	/**
	 * Returns the view casted to the specified interface type in the generation
	 * of this class.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IV getCastedView() {
		return (IV) getView();
	}

	private IMVPPresenter<IV> getPresenter() {
		return presenter;
	}

	/**
	 * Returns the presenter casted to the specified interface type in the
	 * generation of this class.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public IP getCastedPresenter() {
		return (IP) getPresenter();
	}

	/**
	 * This method is used to get a new instance of the presenter. It has to be
	 * overridden by the user to return a new instance of the concrete presenter
	 * intended for this webpage.
	 * 
	 * @return
	 */
	protected abstract IP instanciatePresenter();

	/**
	 * This method is used to get a new instance of the view. It has to be
	 * overridden by the user to return a new instance of the concrete view
	 * intended for this webpage.
	 * 
	 * @return
	 */
	protected abstract IV instanciateView();

	public boolean isInitialized() {
		return initialized;
	}

	/**
	 * Returns the array of required activities to view this webpage
	 * 
	 * @return
	 */
	public IActivity[] getRequiredActivities() {
		return getPresenter().getRequiredActivities();
	}

	/**
	 * This class can be injected through spring. When a class in injected
	 * changes the classname. The classname is used to add automatic id for
	 * style and css rules in vaadin. With his method we asure that the returned
	 * names is always the same.
	 * 
	 * @return
	 */
	public String getNotInjectedName() {
		String className = this.getClass().getName();
		if (className.indexOf(CLASS_INJECTOR_NAME) <= 0) {
			className = this.getClass().getName();
		} else {
			className = className.substring(0, className.indexOf(CLASS_INJECTOR_NAME));
		}
		if (className.indexOf(CLASS_INSTANCE_NAME) <= 0) {
			className = this.getClass().getName();
		} else {
			className = className.substring(0, className.indexOf(CLASS_INSTANCE_NAME));
		}
		return className;
	}

	protected String getDefaultStyleName() {
		return BIIT_STYLE_PREFIX + this.getClass().getSimpleName();
	}
}
