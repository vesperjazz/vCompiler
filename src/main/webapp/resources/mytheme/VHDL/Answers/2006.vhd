library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
-- library call needed when using
-- std logic functions
-- entity declaration
entity logic_cct is
Port ( W,X,Y : in std_logic;
Z : out std_logic);
end logic_cct;
--architecture declaration
architecture D_flow of logic_cct is
begin
Z <= (not(W) and X) or (X and not(Y));
end D_flow;