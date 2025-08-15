package com.zyp.springboot.learn.infra.errorcode;

record CodeRange(int start, int end) {
    boolean overlap(CodeRange range) {
        if (start >= range.start && start <= range.end) {
            return true;
        }

        return end >= range.start && end <= range.end;
    }

    boolean inRange(int code) {
        return code >= this.start && code <= this.end;
    }
}
