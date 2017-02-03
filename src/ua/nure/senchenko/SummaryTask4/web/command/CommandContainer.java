package ua.nure.senchenko.SummaryTask4.web.command;

import java.util.Map;
import java.util.TreeMap;

/**
 * contains a map of all commands and corresponding command names
 */
public class CommandContainer {
	
	private static Map<String, Command> commands = new TreeMap<String, Command>();
	
	static {
		
		commands.put("login", new LoginCommand());
		commands.put("register", new RegisterCommand());
		
		commands.put("changeLang", new ChangeLangCommand());
		
		commands.put("chooseSubject", new ChooseSubjectCommand());
		commands.put("chooseTest", new ChooseTestCommand());
		commands.put("submitTest", new GradeTestCommand());
		
		commands.put("goToProfile", new ShowProfileCommand());
		commands.put("goHome", new GoHomeCommand());
		commands.put("navigateToTestCreation", new ShowTestCreationCommand());
		commands.put("navigateToQuestionCreation", new ShowCreateQuestionCommand());
		commands.put("navigateToAnswerCreation", new ShowCreateAnswerCommand());
		commands.put("navigateToTestEdit", new ShowTestEditCommand());
		commands.put("navigateToQuestionEdit", new ShowQuestionEditCommand());
		commands.put("navigateToAnswerEdit", new ShowAnswerEditCommand());
		
		commands.put("createTest", new CreateTestCommand());
		commands.put("createQuestion", new CreateQuestionCommand());
		commands.put("createAnswer", new CreateAnswerCommand());
		
		commands.put("updateAnswer", new UpdateAnswerCommand());
		commands.put("updateQuestion", new UpdateQuestionCommand());
		commands.put("updateTest", new UpdateTestCommand());
		
		commands.put("deleteTest", new DeleteTestCommand());
		commands.put("deleteQuestion", new DeleteQuestionCommand());
		commands.put("deleteAnswer", new DeleteAnswerCommand());
		
		commands.put("changeStatus", new ChangeStatusCommand());
		commands.put("logout", new LogoutCommand());

	}

	
	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			return commands.get("noCommand"); 
		}
		
		return commands.get(commandName);
	}

}
