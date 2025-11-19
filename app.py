import streamlit as st
import pandas as pd
import matplotlib.pyplot as plt
import subprocess

st.title("GCS Unhoused Simulation")

NUM_AGENTS = 10
MONTHS = 12

# --- Agent sliders ---
agent_data = []
for i in range(NUM_AGENTS):
    st.subheader(f"Agent {i+1}")
    assets = st.slider(f"Assets (Agent {i+1})", 0, 100, 10, key=f"assets_{i}")
    disability = st.checkbox(f"Disabled", value=False, key=f"disability_{i}")
    job_training = st.checkbox(f"Job Training", value=False, key=f"jobtraining_{i}")
    homeless = st.checkbox(f"Initially Homeless", value=False, key=f"homeless_{i}")
    ever_homeless = st.checkbox(f"Ever Homeless Before", value=False, key=f"everhomeless_{i}")
    agent_data.append((assets, disability, job_training, homeless, ever_homeless))

# --- Run simulation ---
if st.button("Run Simulation"):
    st.info("Preparing input file for Java simulation...")

    # Write input for Java
    with open("agent_input.txt", "w") as f:
        f.write("each\n")
        for data in agent_data:
            f.write(f"{data[0]},{str(data[1]).lower()},{str(data[2]).lower()},{str(data[3]).lower()},{str(data[4]).lower()}\n")

    st.info("Running Java simulation...")

    try:
        subprocess.run(["java", "Main"], check=True)
    except subprocess.CalledProcessError:
        st.error("Error: Make sure Main.class exists and is compiled.")
        st.stop()

    st.success("Simulation complete! Plotting results...")

    # --- Plot Assets ---
    assets_df = pd.read_csv("assets.csv")
    months = assets_df['Month']
    plt.figure(figsize=(10,6))
    for agent in assets_df.columns[1:]:
        plt.plot(months, assets_df[agent], marker='o', label=agent)
    plt.xlabel("Month")
    plt.ylabel("Asset Value (0-100)")
    plt.title("Agent Asset Values")
    plt.legend()
    plt.grid(True)
    st.pyplot(plt.gcf(), clear_figure=True)

    # --- Homeless ---
    homeless_df = pd.read_csv("homeless.csv")
    agent_cols = homeless_df.columns[1:]
    homeless_df[agent_cols] = homeless_df[agent_cols].astype(int)
    homeless_totals = homeless_df[agent_cols].sum(axis=1)
    plt.figure(figsize=(10,6))
    plt.bar(homeless_df['Month'], homeless_totals, color='skyblue')
    plt.ylim(0, NUM_AGENTS)
    plt.xlabel("Month")
    plt.ylabel("Total Homeless")
    plt.title("Total Homeless Agents")
    plt.grid(axis='y')
    st.pyplot(plt.gcf(), clear_figure=True)

    # --- Disability ---
    disability_df = pd.read_csv("disability.csv")
    plt.figure(figsize=(10,6))
    plt.bar(disability_df['Month'], disability_df['Disability_Total'], color='orange')
    plt.ylim(0, NUM_AGENTS)
    plt.xlabel("Month")
    plt.ylabel("Total Disabled")
    plt.title("Total Disabled Agents")
    plt.grid(axis='y')
    st.pyplot(plt.gcf(), clear_figure=True)

    # --- Job Training ---
    jobtraining_df = pd.read_csv("jobtraining.csv")
    plt.figure(figsize=(10,6))
    plt.bar(jobtraining_df['Month'], jobtraining_df['JobTraining_Total'], color='green')
    plt.ylim(0, NUM_AGENTS)
    plt.xlabel("Month")
    plt.ylabel("Total Job Training")
    plt.title("Total Agents with Job Training")
    plt.grid(axis='y')
    st.pyplot(plt.gcf(), clear_figure=True)
