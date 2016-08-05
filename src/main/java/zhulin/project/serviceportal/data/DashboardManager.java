package zhulin.project.serviceportal.data;

import java.util.List;

import zhulin.project.serviceportal.Dashboard;
import zhulin.project.serviceportal.DashboardObjectMapping;
import zhulin.project.serviceportal.DashboardObject;

public interface DashboardManager {
	public Dashboard loadDashboard(String dashboardName);
	public List<Dashboard> loadDashboards();
	public void createDashboard(Dashboard dashboard);
	public void updateDashboard(Dashboard dashboard);
	
	public DashboardObject loadDashboardObject(String dashboardObject);
	public List<DashboardObject> loadDashboardObjects();
	public DashboardObjectMapping loadDashboardMapping(String deviceType);
	public void saveDashboardMapping(DashboardObjectMapping objectMapping);
}
