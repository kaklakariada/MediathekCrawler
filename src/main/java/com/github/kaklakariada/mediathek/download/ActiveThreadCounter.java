package com.github.kaklakariada.mediathek.download;

import java.util.concurrent.Phaser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ActiveThreadCounter {
    private static final Logger LOG = LoggerFactory.getLogger(ActiveThreadCounter.class);

    private final Phaser phaser;

    private final int phase;

    ActiveThreadCounter() {
        phaser = new Phaser(1);
        phase = phaser.getPhase();
        // threadStarted();
    }

    void threadStarted() {
        phaser.register();
        logPhaserState("Registered new task");
    }

    void threadFinished() {
        phaser.arrive();
        logPhaserState("Task finished");
    }

    void await() {
        // phaser.register();
        // while (!phaser.isTerminated()) {
        // }
        logPhaserState("Waiting for phase " + phase + "...");
        phaser.awaitAdvance(phase);
        logPhaserState("Phaser finished");

        // phaser.arriveAndDeregister();
    }

    private void logPhaserState(String message) {
        LOG.trace("{}: phase={}, parties={}, unarrived={}, arrived={}, terminated={}", message, phaser.getPhase(),
                phaser.getRegisteredParties(), phaser.getUnarrivedParties(), phaser.getArrivedParties(),
                phaser.isTerminated());
    }
}
