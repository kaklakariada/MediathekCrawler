package com.github.kaklakariada.mediathek.download;

import java.util.concurrent.Phaser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ActiveThreadCounter {
    private static final Logger LOG = LoggerFactory.getLogger(ActiveThreadCounter.class);

    private final Phaser phaser;

    private final int phase;

    ActiveThreadCounter() {
        phaser = new Phaser();
        phase = phaser.getPhase();
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
        LOG.debug("Waiting for phaser {} to reach phase {}", phaser, phase);
        phaser.awaitAdvance(phase);
        LOG.debug("Phaser finished: {}", phaser);
    }

    private void logPhaserState(String message) {
        LOG.trace("{}: phase={}, parties={}, unarrived={}, arrived={}, terminated={}", message, phaser.getPhase(),
                phaser.getRegisteredParties(), phaser.getUnarrivedParties(), phaser.getArrivedParties(),
                phaser.isTerminated());
    }
}
