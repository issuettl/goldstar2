package lgekorfrm.sso.domain;


import java.util.Map;

public class PipeLineHistory {

    private Long id;
    private Long userStorageId;
    private String userStorageName;
    private Long durationTime;
    private Long startTime;
    private Long endTime;
    private Long pipeLineId;
    private String pipeLineName;
    private String error;
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserStorageId() {
        return userStorageId;
    }

    public void setUserStorageId(Long userStorageId) {
        this.userStorageId = userStorageId;
    }

    public String getUserStorageName() {
        return userStorageName;
    }

    public void setUserStorageName(String userStorageName) {
        this.userStorageName = userStorageName;
    }

    public Long getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Long durationTime) {
        this.durationTime = durationTime;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Long getPipeLineId() {
        return pipeLineId;
    }

    public void setPipeLineId(Long pipeLineId) {
        this.pipeLineId = pipeLineId;
    }

    public String getPipeLineName() {
        return pipeLineName;
    }

    public void setPipeLineName(String pipeLineName) {
        this.pipeLineName = pipeLineName;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public PipeLineResult getResults() {
        return results;
    }

    public void setResults(PipeLineResult results) {
        this.results = results;
    }

    public enum Status {
        SUCCESS, FAILED
    }

    private PipeLineResult results;

    public static class PipeLineResult {
        private Object finalOutput;
        private Object initialInput;
        private Map<String, TaskResult> taskResults;

        public Object getFinalOutput() {
            return finalOutput;
        }

        public void setFinalOutput(Object finalOutput) {
            this.finalOutput = finalOutput;
        }

        public Object getInitialInput() {
            return initialInput;
        }

        public void setInitialInput(Object initialInput) {
            this.initialInput = initialInput;
        }

        public Map<String, TaskResult> getTaskResults() {
            return taskResults;
        }

        public void setTaskResults(Map<String, TaskResult> taskResults) {
            this.taskResults = taskResults;
        }
    }

    public static class TaskResult {
        Map<String, Object> inputData;
        private Object outputData;
        private String stdout;
        private String stderr;
        private Status status;
        private Long duration;
        private int index;

        public Map<String, Object> getInputData() {
            return inputData;
        }

        public void setInputData(Map<String, Object> inputData) {
            this.inputData = inputData;
        }

        public Object getOutputData() {
            return outputData;
        }

        public void setOutputData(Object outputData) {
            this.outputData = outputData;
        }

        public String getStdout() {
            return stdout;
        }

        public void setStdout(String stdout) {
            this.stdout = stdout;
        }

        public String getStderr() {
            return stderr;
        }

        public void setStderr(String stderr) {
            this.stderr = stderr;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Long getDuration() {
            return duration;
        }

        public void setDuration(Long duration) {
            this.duration = duration;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
