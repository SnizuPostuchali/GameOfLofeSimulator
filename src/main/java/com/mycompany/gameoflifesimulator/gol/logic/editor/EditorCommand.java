package com.mycompany.gameoflifesimulator.gol.logic.editor;

import com.mycompany.gameoflifesimulator.gol.command.Command;
import com.mycompany.gameoflifesimulator.gol.state.EditorState;

public interface EditorCommand extends Command<EditorState> {
    @Override
    void execute(EditorState editorState);
}
