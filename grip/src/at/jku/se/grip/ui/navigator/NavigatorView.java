package at.jku.se.grip.ui.navigator;

import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class NavigatorView extends CustomComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3516870999995791498L;
	
	public static final String ID = "dashboard-menu";
	private static final String BUTTON_WIDTH = "180px";
	
	private VerticalLayout menuVerticalLayout = null;
	private HorizontalLayout logoWrapperHorizontalLayout = null;
	private Button overviewButton = null;
	private Button drawboardButton = null;
	private Button robotsButton = null;
	private Button usersButton = null;
	private Button signOutButton = null;
	
	public NavigatorView() {
		super();
		init();
	}
	
	private void init() {
		this.addStyleName(ValoTheme.MENU_ROOT);
		this.setSizeUndefined();
		this.setId(ID);
		this.setSizeUndefined();
		this.setCompositionRoot(getMenuVerticalLayout());
	}
	
	public VerticalLayout getMenuVerticalLayout() {
		if(menuVerticalLayout == null) {
			menuVerticalLayout = new VerticalLayout();
			menuVerticalLayout.addStyleName(ValoTheme.MENU_PART);
			menuVerticalLayout.addStyleName("no-vertical-drag-hints");
			menuVerticalLayout.addStyleName("no-horizontal-drag-hints");
			menuVerticalLayout.setWidth(null);
			menuVerticalLayout.setHeight("100%");
			menuVerticalLayout.setSpacing(true);

			menuVerticalLayout.addComponent(getLogoWrapperHorizontalLayout());
			menuVerticalLayout.addComponent(getOverviewButton());
			menuVerticalLayout.addComponent(getDrawboardButton());
			menuVerticalLayout.addComponent(getRobotsButton());
	        menuVerticalLayout.addComponent(getUsersButton());
	        menuVerticalLayout.addComponent(getSignOutButton());
		}
		return menuVerticalLayout;
	}
	
	/*public MHorizontalLayout getLogoHorizontalLayout() {
		if(logoHorizontalLayout == null) {
			logoHorizontalLayout = new MHorizontalLayout();
			logoHorizontalLayout.withStyleName(ValoTheme.MENU_PART);
			logoHorizontalLayout.withStyleName(ValoTheme.MENU_PART_LARGE_ICONS);
			logoHorizontalLayout.withFullWidth();

			Label logo = new Label(FontAwesome.USER.getHtml(), ContentMode.HTML);
			logo.setSizeUndefined();
			logo.setPrimaryStyleName(ValoTheme.MENU_LOGO);

			logoHorizontalLayout.addComponent(logo);
		}
		return logoHorizontalLayout;
	}*/
	
	public HorizontalLayout getLogoWrapperHorizontalLayout() {
		if(logoWrapperHorizontalLayout == null) {
			Label logoIcon = new Label("<h1>"+FontAwesome.CIRCLE_O_NOTCH.getHtml()+"</h1>", ContentMode.HTML);
			Label logoText = new Label("<h2><strong>GRIP</strong></h2>", ContentMode.HTML);
			Label labelSpace = new Label("&nbsp;&nbsp;&nbsp;", ContentMode.HTML);
			logoWrapperHorizontalLayout = new HorizontalLayout(logoIcon, labelSpace, logoText);
			logoWrapperHorizontalLayout.setComponentAlignment(logoIcon, Alignment.MIDDLE_CENTER);
			logoWrapperHorizontalLayout.setComponentAlignment(labelSpace, Alignment.MIDDLE_CENTER);
			logoWrapperHorizontalLayout.setComponentAlignment(logoText, Alignment.MIDDLE_CENTER);
			logoWrapperHorizontalLayout.setPrimaryStyleName(ValoTheme.MENU_TITLE);
		}
		return logoWrapperHorizontalLayout;
	}
	
	public Button getOverviewButton() {
		if(overviewButton == null) {
			overviewButton = new Button();
			overviewButton.setIcon(FontAwesome.HOME);
			overviewButton.setPrimaryStyleName(ValoTheme.MENU_ITEM);
			overviewButton.setCaption("Overview");
			overviewButton.setDescription("Switch to home screen.");
			overviewButton.setSizeUndefined();
			overviewButton.setWidth(BUTTON_WIDTH);
		}
		return overviewButton;
	}
	
	public Button getDrawboardButton() {
		if(drawboardButton == null) {
			drawboardButton = new Button();
			drawboardButton.setIcon(FontAwesome.PENCIL);
			drawboardButton.setPrimaryStyleName(ValoTheme.MENU_ITEM);
			drawboardButton.setCaption("Drawboard");
			drawboardButton.setDescription("Switch to the drawboard.");
			drawboardButton.setSizeUndefined();
			drawboardButton.setWidth(BUTTON_WIDTH);
		}
		return drawboardButton;
	}
	
	public Button getRobotsButton() {
		if(robotsButton == null) {
			robotsButton = new Button();
			robotsButton.setIcon(FontAwesome.GEARS);
			robotsButton.setPrimaryStyleName(ValoTheme.MENU_ITEM);
			robotsButton.setCaption("Robots");
			robotsButton.setDescription("Edit robots.");
			robotsButton.setSizeUndefined();
			robotsButton.setWidth(BUTTON_WIDTH);
		}
		return robotsButton;
	}
	
	public Button getUsersButton() {
		if(usersButton == null) {
			usersButton = new Button();
			usersButton.setIcon(FontAwesome.USERS);
			usersButton.setPrimaryStyleName(ValoTheme.MENU_ITEM);
			usersButton.setCaption("Users");
			usersButton.setDescription("Edit users.");
			usersButton.setSizeUndefined();
			usersButton.setWidth(BUTTON_WIDTH);
		}
		return usersButton;
	}
	
	public Button getSignOutButton() {
		if(signOutButton == null) {
			signOutButton = new Button();
			signOutButton.setIcon(FontAwesome.SIGN_OUT);
			signOutButton.setPrimaryStyleName(ValoTheme.MENU_ITEM);
			signOutButton.setCaption("Sing Out");
			signOutButton.setDescription("Sign out.");
			signOutButton.setSizeUndefined();
			signOutButton.setWidth(BUTTON_WIDTH);
		}
		return signOutButton;
	}
}
