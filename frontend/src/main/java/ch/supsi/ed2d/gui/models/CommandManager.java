package ch.supsi.ed2d.gui.models;

import java.util.*;

public abstract class CommandManager<T> {


    public void addCommand(ReversibleCommand<T> command)
    {
        commands.add(command);
    }

    protected List<ReversibleCommand<T>> commands = new ArrayList<>();
    protected Stack<ReversibleCommand<T>> StackUndo =new Stack<>();

    public void undo() {

        if(commands.size()==0)
            return;

        var command = commands.remove(commands.size() - 1);

        if (command == null)
            return;

        command.undo();
        StackUndo.add(command);
    }


    public void redo() {
        if(StackUndo.isEmpty())
            return;

        var command = StackUndo.pop();

        if (command == null)
            return;

        command.execute();
        commands.add(command);
    }

    public void execute()
    {
        commands.forEach(Command::execute);
    }

    public void clear()
    {
        StackUndo.clear();
        commands.clear();
    }

}
