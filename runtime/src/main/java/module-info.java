module sms.runtime {
    requires sms.service;
    requires sms.filestore;
    uses sms.service.StudentService;
}