package at.jku.se.grip.ui.navigator;

import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.themes.ValoTheme;

public class NavigatorView extends MVerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3516870999995791498L;
	
	private MHorizontalLayout logoHorizontalLayout = null;
	private MVerticalLayout buttonVerticalLayout = null;
	private MVerticalLayout btnSpacerVerticalLayout = null;
	private MButton overviewButton = null;
	
	public NavigatorView() {
		super();
		init();
	}
	
	private void init() {
		this.setStyleName("sidebar");
		this.setStyleName(ValoTheme.MENU_PART);
		this.withFullHeight().withMargin(false).withSpacing(false);
		this.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		this.addComponent(getLogoHorizontalLayout());
		this.addComponent(getButtonVerticalLayout());
	}
	
	public MHorizontalLayout getLogoHorizontalLayout() {
		if(logoHorizontalLayout == null) {
			logoHorizontalLayout = new MHorizontalLayout();
			logoHorizontalLayout.setIcon(FontAwesome.TRANSGENDER);
			logoHorizontalLayout.withStyleName(ValoTheme.MENU_PART_LARGE_ICONS);
			logoHorizontalLayout.withStyleName("navigator-logo").withFullWidth().withHeight("50px");
		}
		return logoHorizontalLayout;
	}
	
	public MVerticalLayout getButtonVerticalLayout() {
		if(buttonVerticalLayout == null) {
			buttonVerticalLayout = new MVerticalLayout();
			buttonVerticalLayout.setStyleName("navigator-spacer");
			buttonVerticalLayout.withFullWidth().withFullHeight().withMargin(false);
			buttonVerticalLayout.setDefaultComponentAlignment(Alignment.TOP_LEFT);
			buttonVerticalLayout.addComponent(getOverviewButton());
			buttonVerticalLayout.addComponent(getBtnSpacerVerticalLayout());
		}
		return buttonVerticalLayout;
	}
	
	public MVerticalLayout getBtnSpacerVerticalLayout() {
		if(btnSpacerVerticalLayout == null) {
			btnSpacerVerticalLayout = new MVerticalLayout().withFullHeight();
			btnSpacerVerticalLayout.withCaption("horst");
		}
		return btnSpacerVerticalLayout;
	}
	
	public MButton getOverviewButton() {
		if(overviewButton == null) {
			overviewButton = new MButton();
			overviewButton.setIcon(FontAwesome.HOME);
			overviewButton.setPrimaryStyleName("valo-menu-item");
			overviewButton.withCaption("Home").withDescription("Switch to home screen.");//.withWidth("200px");
		}
		return overviewButton;
	}
}
