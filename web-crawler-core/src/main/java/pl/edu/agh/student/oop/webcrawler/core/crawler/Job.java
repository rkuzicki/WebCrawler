package pl.edu.agh.student.oop.webcrawler.core.crawler;

/**
 * {@link Job} is an interface which represents a work to be done. It is normally exeuted by a {@link JobService}.
 */
public interface Job {
    void execute(JobService jobService);
}
