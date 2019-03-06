package ui.model.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class UIFacade implements UIFacadeInterface, PropertyChangeListener{
	
	private static UIFacade uifInstance = null;
	private View view;
	private PropertyChangeSupport support;
	
	
	private UIFacade() {
		this.view = new View("Tablr");
		support = new PropertyChangeSupport(this);
		this.view.addPropertyChangeListener(this);
	}
	/**
	* Creates an UIFacade instance only once.
	* Returns the only existing instance.
	*
	* @post uifInstance is instanciated
	* | new.getInstance == UIFacadeInstance
	*
	* @notes
	* synchronized makes sure that every thread is synchronized and
	* prevents creating another instance in a other thread.
	**/
	
	public static synchronized UIFacade getInstance() {
		if (uifInstance == null) {
			uifInstance = new UIFacade();
		}
		return uifInstance;
	}
	
	public void show() {
		this.view.show();
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }
 
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }
	
	@Override
	public void setTableNames(List<String> tableNames) {
		view.setTablesViewModeListValues(tableNames);
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("UIfacade propertyChange fired");
		this.support.firePropertyChange(evt);
	}

}
