import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'school',
                loadChildren: './school/school.module#MySchoolSchoolModule'
            },
            {
                path: 'school-year',
                loadChildren: './school-year/school-year.module#MySchoolSchoolYearModule'
            },
            {
                path: 'school-school-year',
                loadChildren: './school-school-year/school-school-year.module#MySchoolSchoolSchoolYearModule'
            },
            {
                path: 'leave-holi-day',
                loadChildren: './leave-holi-day/leave-holi-day.module#MySchoolLeaveHoliDayModule'
            },
            {
                path: 'period-type',
                loadChildren: './period-type/period-type.module#MySchoolPeriodTypeModule'
            },
            {
                path: 'period',
                loadChildren: './period/period.module#MySchoolPeriodModule'
            },
            {
                path: 'cycle',
                loadChildren: './cycle/cycle.module#MySchoolCycleModule'
            },
            {
                path: 'level',
                loadChildren: './level/level.module#MySchoolLevelModule'
            },
            {
                path: 'diploma',
                loadChildren: './diploma/diploma.module#MySchoolDiplomaModule'
            },
            {
                path: 'class-room',
                loadChildren: './class-room/class-room.module#MySchoolClassRoomModule'
            },
            {
                path: 'subject',
                loadChildren: './subject/subject.module#MySchoolSubjectModule'
            },
            {
                path: 'room-type',
                loadChildren: './room-type/room-type.module#MySchoolRoomTypeModule'
            },
            {
                path: 'room',
                loadChildren: './room/room.module#MySchoolRoomModule'
            },
            {
                path: 'days',
                loadChildren: './days/days.module#MySchoolDaysModule'
            },
            {
                path: 'program',
                loadChildren: './program/program.module#MySchoolProgramModule'
            },
            {
                path: 'rubric',
                loadChildren: './rubric/rubric.module#MySchoolRubricModule'
            },
            {
                path: 'rubric-amount',
                loadChildren: './rubric-amount/rubric-amount.module#MySchoolRubricAmountModule'
            },
            {
                path: 'country',
                loadChildren: './country/country.module#MySchoolCountryModule'
            },
            {
                path: 'actor',
                loadChildren: './actor/actor.module#MySchoolActorModule'
            },
            {
                path: 'actor-name',
                loadChildren: './actor-name/actor-name.module#MySchoolActorNameModule'
            },
            {
                path: 'address',
                loadChildren: './address/address.module#MySchoolAddressModule'
            },
            {
                path: 'identifier-type',
                loadChildren: './identifier-type/identifier-type.module#MySchoolIdentifierTypeModule'
            },
            {
                path: 'actor-identifier-number',
                loadChildren: './actor-identifier-number/actor-identifier-number.module#MySchoolActorIdentifierNumberModule'
            },
            {
                path: 'image',
                loadChildren: './image/image.module#MySchoolImageModule'
            },
            {
                path: 'student',
                loadChildren: './student/student.module#MySchoolStudentModule'
            },
            {
                path: 'payment',
                loadChildren: './payment/payment.module#MySchoolPaymentModule'
            },
            {
                path: 'regimen',
                loadChildren: './regimen/regimen.module#MySchoolRegimenModule'
            },
            {
                path: 'enrolment',
                loadChildren: './enrolment/enrolment.module#MySchoolEnrolmentModule'
            },
            {
                path: 'responsible',
                loadChildren: './responsible/responsible.module#MySchoolResponsibleModule'
            },
            {
                path: 'relationship-type',
                loadChildren: './relationship-type/relationship-type.module#MySchoolRelationshipTypeModule'
            },
            {
                path: 'actor-relationship',
                loadChildren: './actor-relationship/actor-relationship.module#MySchoolActorRelationshipModule'
            },
            {
                path: 'employee',
                loadChildren: './employee/employee.module#MySchoolEmployeeModule'
            },
            {
                path: 'teacher',
                loadChildren: './teacher/teacher.module#MySchoolTeacherModule'
            },
            {
                path: 'teacher-subject-school-year',
                loadChildren: './teacher-subject-school-year/teacher-subject-school-year.module#MySchoolTeacherSubjectSchoolYearModule'
            },
            {
                path: 'salary',
                loadChildren: './salary/salary.module#MySchoolSalaryModule'
            },
            {
                path: 'job',
                loadChildren: './job/job.module#MySchoolJobModule'
            },
            {
                path: 'staff',
                loadChildren: './staff/staff.module#MySchoolStaffModule'
            },
            {
                path: 'staff-job',
                loadChildren: './staff-job/staff-job.module#MySchoolStaffJobModule'
            },
            {
                path: 'class-session-type',
                loadChildren: './class-session-type/class-session-type.module#MySchoolClassSessionTypeModule'
            },
            {
                path: 'class-session',
                loadChildren: './class-session/class-session.module#MySchoolClassSessionModule'
            },
            {
                path: 'evaluation-type',
                loadChildren: './evaluation-type/evaluation-type.module#MySchoolEvaluationTypeModule'
            },
            {
                path: 'evaluation',
                loadChildren: './evaluation/evaluation.module#MySchoolEvaluationModule'
            },
            {
                path: 'student-evaluation',
                loadChildren: './student-evaluation/student-evaluation.module#MySchoolStudentEvaluationModule'
            },
            {
                path: 'evaluation-mode',
                loadChildren: './evaluation-mode/evaluation-mode.module#MySchoolEvaluationModeModule'
            },
            {
                path: 'student-missing-session',
                loadChildren: './student-missing-session/student-missing-session.module#MySchoolStudentMissingSessionModule'
            },
            {
                path: 'teacher-missing-session',
                loadChildren: './teacher-missing-session/teacher-missing-session.module#MySchoolTeacherMissingSessionModule'
            },
            {
                path: 'asking-permission',
                loadChildren: './asking-permission/asking-permission.module#MySchoolAskingPermissionModule'
            },
            {
                path: 'permission-agreement',
                loadChildren: './permission-agreement/permission-agreement.module#MySchoolPermissionAgreementModule'
            },
            {
                path: 'student-diploma',
                loadChildren: './student-diploma/student-diploma.module#MySchoolStudentDiplomaModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MySchoolEntityModule {}
