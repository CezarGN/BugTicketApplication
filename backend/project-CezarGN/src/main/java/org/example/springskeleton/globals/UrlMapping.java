package org.example.springskeleton.globals;

public class UrlMapping {

  public static final String API_PATH = "/bugTicketApplication";

  public static final String AUTH_PATH = API_PATH + "/auth";
  public static final String ADMIN_PROJECT_PATH = API_PATH + "/admin_project";
  public static final String ADMIN_DEVELOPER_PATH = API_PATH + "/admin_developer";
  public static final String DEVELOPER_PROJECT_PATH = API_PATH + "/developer_project";
  public static final String DEVELOPER_BUG_PATH = API_PATH + "/developer_bug";

  public static final String SIGN_UP = "/sign_up";
  public static final String SIGN_IN = "/sign_in";
  public static final String CREATE_PROJECT = "/create_project";
  public static final String GET_PROJECTS = "/get_projects";
  public static final String GET_DEVELOPERS = "/get_developers";
  public static final String GET_IDLE_DEVELOPERS = "/get_idle_developers";
  public static final String ID_STRING = "/{id}";
  public static final String PROJECT_ID_STRING = "/{projectId}";
  public static final String DELETE_PROJECT = "/delete_project" + ID_STRING;
  public static final String UPDATE_PROJECT = "/update_project" + ID_STRING;
  public static final String UPDATE_DEVELOPER = "/update_developer" + ID_STRING;
  public static final String DELETE_DEVELOPER = "/delete_developer" + ID_STRING;
  public static final String GET_BUGS = "/get_bugs" + ID_STRING + PROJECT_ID_STRING;
  public static final String CREATE_BUG = "/create_bug" + ID_STRING;
  public static final String UPDATE_BUG = "/update_bug" + ID_STRING;
  public static final String DELETE_BUG = "/delete_bug" + ID_STRING;
  public static final String GET_DEVELOPER_PROJECT = "/get_developer_project" + ID_STRING;
  public static final String GET_BUG_BY_ID = "/get_bug" + ID_STRING;
  public static final String GET_DEVELOPERS_ON_PROJECT = "/get_developers_on_project" + ID_STRING;
  public static final String GET_DEVELOPERS_ON_PROJECT_AND_IDLE = "/get_developers_on_project_and_idle" + ID_STRING;
  public static final String GET_DEVELOPER_BUGS = "/get_developer_bugs" + ID_STRING + PROJECT_ID_STRING;
  public static final String GET_PROJECT_BUGS = "/get_project_bugs" + ID_STRING;

}
