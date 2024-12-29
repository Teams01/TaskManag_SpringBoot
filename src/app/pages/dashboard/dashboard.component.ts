import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { Router } from "@angular/router";
import { AuthService } from "../services/auth.service";
import { NgbModal, NgbModalConfig } from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: "app-dashboard",
  templateUrl: "./dashboard.component.html",
  styleUrls: ["./dashboard.component.scss"],
  providers: [NgbModalConfig, NgbModal],
})
export class DashboardComponent implements OnInit {
  public projects: any[] = []; // Store projects list
  public workspaceForm: FormGroup;
  public successMessage: string = '';
  public errorMessage: string = '';

  constructor(
    private router: Router,
    private authService: AuthService,
    config: NgbModalConfig,
    private modalService: NgbModal,
    private fb: FormBuilder
  ) {
    config.backdrop = "static";
    config.keyboard = false;
  }

  ngOnInit(): void {
    this.initializeWorkspaceForm();
    this.loadProjects();
  }

  // Initialize the workspace form
  initializeWorkspaceForm(): void {
    this.workspaceForm = this.fb.group({
      name: ["", Validators.required],
      description: [""]
    });
  }

  // Open modal for adding workspace
  open(content: any): void {
    this.modalService.open(content);
  }

  // Load projects from the server
  loadProjects(): void {
    this.authService.getProjectByuser().subscribe({
      next: (response) => {
        this.projects = response;
        console.log("Projects loaded:", this.projects);
      },
      error: (err) => {
        console.error("Error loading projects:", err);
      }
    });
  }

  // Add new workspace
  addWorkspace(): void {
    const ownerID = localStorage.getItem("userID");
    if (!ownerID) {
      console.error("User ID not found in local storage");
      return;
    }

    const workspaceData = {
      ...this.workspaceForm.value,
      owner: ownerID, // Add owner ID to the workspace data
    };

    this.authService.createWorkspace(workspaceData).subscribe({
      next: (response) => {
        console.log("Workspace created successfully", response);
        this.successMessage = "Workspace created successfully!";
        this.errorMessage = ''; // Clear any previous error
        setTimeout(() => {
          this.router.navigate(["/dashboard"]);
        }, 2000);
        this.workspaceForm.reset(); // Reset the form
        window.location.reload(); // Reload the page
      },
      error: (error) => {
        console.error("Error creating workspace", error);
        this.errorMessage = "Failed to create workspace. Please try again.";
        this.successMessage = ''; // Clear any previous success message
      }
    });
  }
}
