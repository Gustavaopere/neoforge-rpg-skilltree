# Create Propulsion: Simulated

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db819c8471cccca2246538
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Propulsion: Simulated
- **Arquivo JAR:** `createpropulsion-1.1.5.jar`
- **Versão 1.21.1:** 1.1.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Exploração, Automação
- **Função:** Propulsão física para contraptions Sable/Create Aeronautics com thrusters a combustível, Ion Thrusters FE, Solid Fuel Thruster, vector/multiblock thrust, tilt adapters e integração data-driven de combustíveis.
- **Dependências:** Create 6.0.10 + Sable/Create Aeronautics. Source matching 1.1.5 usa Create 6.0.10 e Sable 2.0.3; pack físico usa Sable 2.0.5 e Aeronautics 1.3.2. Integra combustíveis de TFMG, CC&A, Create Diesel Generators e Northstar quando realmente registrados. CC:Tweaked é upstream/inativo no pack atual.
- **Sobreposição:** Interseção concreta com Create Aeronautics: Gadgets & Gizmos 1.1.3 em propulsão/controle, mas não duplicata integral. TFMG/CC&A/CDG/Northstar fornecem combustíveis/energia potenciais; Propulsion continua owner do thruster e da liquidação de thrust.
- **Compatibilidade/Riscos:** Riscos: force-at-point/torque double-application; FE ou fuel parcialmente debitado com thrust integral; multiblock topology perdida em cópia/assembly; cable network processada mais de uma vez/tick; mounted-fluid dupe/loss; Tilt Adapter drift; KubeJS fuel registry stale; Sable 2.0.5 drift; overlap com Gadgets & Gizmos.
- **Observações:** JAR/mod id/runtime 1.1.5 e source matching confirmados. 1.1.5 adiciona Solid Fuel Thruster, fuels data-driven/KubeJS, Advanced Tilt Adapter e Platinum Fluid Vessel; corrige FE parcial, Liquid Vector Thruster, multiblock topology/off-center thrust, cable routing e sync/render.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + release/changelog oficiais Create Propulsion: Simulated 1.1.5 + source oficial Propulsion-Team/create-propulsion-simulated `main` matching 1.1.5.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-propulsion-simulated
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 1.1.5 com force-at-point/torque, fuel/FE settlement, Solid Fuel Thruster, KubeJS fuels, multiblocks, Tilt Adapter, mounted fluids, Sable/Aeronautics e regressões 1.1.5 catalogados.
- **Histórico da decisão:**
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🚀 **Identidade física e source matching confirmados:** `createpropulsion-1.1.5.jar`, mod id `createpropulsion`, runtime `1.1.5`, NeoForge 1.21.1. O source oficial `Propulsion-Team/create-propulsion-simulated` em `main` declara exatamente 1.1.5, Create 6.0.10 e Sable 2.0.3 no ambiente de desenvolvimento; o pack usa Create 6.0.10 e Sable 2.0.5.

## 1. Papel e authority
Create Propulsion: Simulated adiciona propulsão física para veículos/contraptions do stack Sable/Create Aeronautics. O addon owns thrusters, consumo de combustível/FE, adapters e cálculo de força que entrega à física; Sable/Aeronautics continuam authorities do body, transformação espacial e integração física global.

## 2. Famílias de thrusters
A documentação oficial publica três famílias principais: **Thruster** movido a combustível, **Ion Thruster** movido a FE e **Creative Thruster** configurável para teste/creative. Cada família possui recursos/custos próprios; aparência não deve ser usada para inferir combustível ou potência.

## 3. Force-at-point e torque
O addon aplica força em um ponto do veículo, permitindo gerar torque quando o thruster está fora do centro de massa. A mesma ação física não pode ser aplicada duas vezes por hooks distintos; posição, orientação e magnitude precisam usar o frame de referência correto do body Sable.

## 4. Solid Fuel Thruster — 1.1.5
A 1.1.5 adiciona **Solid Fuel Thruster**. Combustíveis sólidos são data-driven e podem ser definidos por datapack/KubeJS. O consumo precisa ser conservativo: cada unidade de thrust efetivo deve debitar exatamente o recurso previsto pela configuração carregada.

## 5. Combustíveis data-driven e KubeJS
O pack contém KubeJS e KubeJS Create, tornando a API/data surface de combustíveis diretamente relevante. Reload de datapack/script deve reconstruir a tabela efetiva sem manter combustível removido, multiplicar entradas ou produzir diferença client/server.

## 6. Combustíveis de addons instalados
A documentação upstream lista integração com TFMG, Create Crafts & Additions, Create Diesel Generators e Northstar Redux, todos presentes fisicamente no pack. A presença do provider não prova que todo fluido/item dele é combustível: a lista data-driven/runtime continua authority.

## 7. Providers upstream não presentes
Immersive Engineering, Mekanism Generators e Stellaris aparecem na matriz pública de combustíveis, mas não foram encontrados top-level na modlist física atual. Esses caminhos permanecem upstream/inativos e não devem ser tratados como dependências do pack.

## 8. Ion Thruster e FE
Ion Thruster consome Forge Energy. A 1.1.5 corrige consumo de FE e cálculos de energia parcial. Em redes com energia insuficiente, thrust e débito precisam convergir para a mesma fração efetiva, sem gerar força integral pagando energia parcial.

## 9. Cable energy routing — 1.1.5
A release retrabalha o roteamento de energia para processar cada rede de cabos uma vez por tick. Isso é gate de performance e conservação: topologias cíclicas ou múltiplos consumidores não podem aplicar transferências repetidas no mesmo tick.

## 10. Liquid/Vector Thrusters
A 1.1.5 corrige armazenamento de combustível do **Liquid Vector Thruster** e informação em goggles. Vectoring deve alterar direção de força sem criar energia adicional; buffer de combustível precisa sobreviver a assembly, unload e restart sem dupe/loss.

## 11. Multiblock thrusters
A release permite configurar thrust e eficiência de fuel/oxidizer de multiblocks e corrige força aplicada fora do centro. Controller topology precisa permanecer única e coerente entre estrutura, body físico e componentes conectados.

## 12. Cópia de topologia
A 1.1.5 corrige multiblock thrusters copiados que perdiam a topologia do controller. Schematic/copy workflows devem reconstruir referências para a nova instância, nunca manter ponteiros para o assembly original.

## 13. Advanced Tilt Adapter
A versão adiciona **Advanced Tilt Adapter** com limites configuráveis de ângulo esquerdo/direito e corrige target drift, motion e angle handling. Target angular deve permanecer limitado pela configuração e não acumular drift ao montar/desmontar o veículo.

## 14. Platinum Fluid Vessel
A 1.1.5 adiciona **Platinum Fluid Vessel** com capacidade publicada como duas vezes a variante-base correspondente. O pack deve tratar a capacidade efetiva do runtime como authority; transferências em contraption não podem duplicar conteúdo durante mount/unmount.

## 15. Mounted fluid storage
A release adiciona suporte a armazenamento de fluido montado para Platinum Tanks/Vessels. Capabilities precisam ser invalidadas/recriadas corretamente ao mover entre world block e contraption state.

## 16. Plumes, partículas e som
A 1.1.5 retrabalha plumes com modos de partículas/shader, efeitos de startup/loop/shutdown e sons dedicados, incluindo Ion Thruster. Render/audio são client-facing; falha ou desativação de efeitos não pode alterar thrust funcional server-side.

## 17. Thrust proporcional ao output efetivo
A release faz thrust e consumo escalarem com output efetivo. O contrato importante é conservation entre output físico e recurso gasto; throttling, falta parcial de recurso e limites de eficiência devem produzir resultados consistentes no servidor.

## 18. ComputerCraft — upstream, não ativo
A build/source possui peripherals para Solid Fuel e multiblock thrusters e corrige integração ComputerCraft. Não há CC:Tweaked/ComputerCraft top-level na modlist atual, portanto essa superfície é registrada como upstream/inativa e deve permanecer optional-classloading safe.

## 19. Sable/Create Aeronautics
Sable 2.0.5 e Aeronautics 1.3.2 estão fisicamente instalados. O source 1.1.5 foi desenvolvido com Sable 2.0.3, então 2.0.5 exige regression smoke-test de force application, body lookup, assembly lifecycle e transforms apesar de satisfazer a linha mínima publicada.

## 20. Overlap com Gadgets & Gizmos
`createthrusters-bundled-V1.1.3.jar` também está presente e oferece componentes de propulsão/controle. A interseção é parcial, não duplicata integral. Evitar que dois blocks/hooks diferentes liquidem a mesma intenção de input/propulsion sobre o mesmo body.

## 21. Client/server e multiplayer
Consumo de fuel/FE, controller topology, thrust, force point e alterações de state devem ser server-authoritative. Modelos, plume, sounds, goggles e UI são client-facing. Múltiplos players operando o mesmo veículo não podem multiplicar uma única ordem de throttle.

## 22. Lifecycle
Testar place/break, assembly/disassembly, copied schematics, chunk unload/reload, server restart com veículo montado, fuel/FE depletion, KubeJS/datapack reload, body rotation, tilt limits e reconnect multiplayer.

## 23. Riscos
1. Force-at-point aplicada duas vezes.
2. Off-center thrust usa frame errado e gera torque incorreto.
3. FE parcial produz thrust integral.
4. Fuel/oxidizer debitado em quantidade diferente do thrust efetivo.
5. Cable network processada múltiplas vezes por tick.
6. Multiblock controller topology se perde ao copiar/assemble.
7. Mounted fluid capability duplica/perde conteúdo.
8. Tilt Adapter acumula target drift.
9. KubeJS/datapack reload deixa fuel registry stale.
10. Provider externo oferece combustível semanticamente incompatível.
11. Sable 2.0.5 altera API/transform esperado pela linha 2.0.3-dev.
12. Gadgets & Gizmos cria overlap de propulsão/input.
13. Path ComputerCraft ausente gera classloading indevido.
14. Render/plume diverge do thruster realmente ativo.

## 24. Matriz de testes
- [ ] Dedicated server inicia com Propulsion 1.1.5 + Create 6.0.10 + Sable 2.0.5 + Aeronautics 1.3.2.
- [ ] Thruster comum consome apenas combustível válido e aplica uma força.
- [ ] Solid Fuel Thruster reflete fuels data-driven do pack.
- [ ] `/reload`/KubeJS reload adiciona/remove fuels sem cache stale.
- [ ] Ion Thruster com FE parcial escala thrust e consumo corretamente.
- [ ] Cable network cíclica é processada uma vez por tick.
- [ ] Liquid Vector Thruster preserva fuel e vector state.
- [ ] Multiblock thruster preserva controller topology em assembly/cópia.
- [ ] Força fora do centro produz torque coerente sem double-application.
- [ ] Advanced Tilt Adapter respeita limites sem drift.
- [ ] Platinum Vessel/Tanks preservam fluid em mount/unmount/restart.
- [ ] Providers TFMG/CC&A/CDG/Northstar só aceitam fuels realmente registrados.
- [ ] Ausência de CC:Tweaked não causa classloading failure.
- [ ] Coexistência com Gadgets & Gizmos não multiplica thrust por uma ação.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 25. Evidências e limites
A modlist física confirma JAR/mod id/runtime 1.1.5 e o stack atual. A release oficial 1.1.5 confirma Solid Fuel Thruster, fuels data-driven/KubeJS, Advanced Tilt Adapter, Platinum Vessel, fixes de FE/vector/multiblock/topology/energy routing e rework de sync/render. O source matching confirma 1.1.5, Create 6.0.10 e Sable 2.0.3-dev. Valores absolutos de thrust, consumo e recipes não pinados permanecem fail-closed.

> 🔒 **Boundary canônico:** Propulsion decide thrusters e resource settlement; Sable/Aeronautics decide o body/física global. Cada tick deve liquidar recurso e força exatamente uma vez no mesmo state authoritative.
