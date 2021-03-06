module com.mycompany.gameoflifesimulator {
    requires javafx.controls;
    requires javafx.graphics;

    exports com.mycompany.gameoflifesimulator.gol.viewModel;
    exports com.mycompany.gameoflifesimulator.gol.model;
    exports com.mycompany.gameoflifesimulator.gol.util;
    exports com.mycompany.gameoflifesimulator.gol.util.event;
    exports com.mycompany.gameoflifesimulator.gol.view;
    exports com.mycompany.gameoflifesimulator.gol.logic;
    exports com.mycompany.gameoflifesimulator.gol;
}
