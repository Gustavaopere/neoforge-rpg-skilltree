# Create Nuclear

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8108850beb9d67d0863f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Nuclear
- **Arquivo JAR:** `createnuclear-1.3.2-beta.3-neoforge.jar`
- **Versão 1.21.1:** 1.3.2-beta.3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Automação
- **Função:** Expande o Create com tecnologia nuclear, incluindo reatores, radiação, recursos e sistemas de geração/uso de energia associados.
- **Dependências:** Create obrigatório; source matching foi desenvolvido contra Create 6.0.7 e o pack usa 6.0.10, portanto há version-drift a testar. Curios 9.5.1 está presente para Radiation Meter. A build é Beta/experimental.
- **Sobreposição:** Create: New Age 1.2.0 e Create Crafts & Additions 1.7.0 compartilham geração/conversão de energia, mas Nuclear possui reactor/radiation/fuel chain própria. Auditar loops SU/FE e custos, não presumir redundância.
- **Compatibilidade/Riscos:** Riscos: reactor/rod state stale; fuel depletion/ejection replay; neutron/criticality desync; steam/fluid dupe; SU overflow/readout; Coal Dust/Reinforced Glass tag drift; HEV/Hazmat state stale; Curios meter duplication; Create API drift; loops energéticos com New Age/CC&A; retroprojeção indevida da linha V2.
- **Observações:** JAR/mod id/runtime 1.3.2-beta.3 e source matching `Create-Nuclear-Team/CreateNuclearNeoForge` main confirmados. Deltas exatos 1.3.2: refactor de Coal Dust tags/recipes e atualização de glass tags/recipes para Reinforced Glass. Conteúdo V2 posterior não foi atribuído à build instalada.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + release oficial 1.3.2-beta.3 + source/README/CHANGELOG oficiais Create-Nuclear-Team/CreateNuclearNeoForge matching.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/createnuclear
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 1.3.2-beta.3 com reactor/criticality, neutron flux, fuel rods, heat exchanger/steam/SU, radiation, HEV/Hazmat, Curios Radiation Meter e deltas Coal Dust/Reinforced Glass catalogados.
- **Histórico da decisão:** Histórico: houve sugestão anterior de remoção por adicionar uma ramificação nuclear completa e por a build estar em beta; posteriormente o usuário manteve addons Create de que gosta. Esse histórico não equivale a uma decisão atual. O mod permanece instalado e sem decisão final.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> ☢️ **Identidade física e source matching confirmados:** `createnuclear-1.3.2-beta.3-neoforge.jar`, mod id `createnuclear`, runtime `1.3.2-beta.3`, NeoForge 1.21.1. O repositório oficial `Create-Nuclear-Team/CreateNuclearNeoForge` em `main` declara exatamente `1.3.2-beta.3`. Esta é uma linha **Beta/experimental**; conteúdo da linha V2 posterior para outro alvo não é retroprojetado.

## 1. Papel e authority
Create Nuclear adiciona mineração/processamento de materiais nucleares, **Nuclear Reactor** atualizável, radiação, geração de vapor/energia cinética e equipamentos de proteção/monitoramento. O addon owns reactor state, fuels, radiation, seus materiais e máquinas; Create continua authority da cinética/stress e das infraestruturas base reutilizadas.

## 2. Reactor Core e estrutura
A documentação oficial descreve um reator nuclear atualizável formado por componentes próprios. Formação, desformação e reconstrução do reator precisam preservar state coerente entre rods, coolers, control elements e geração. Componentes removidos não podem continuar contribuindo após neighbor/chunk update.

## 3. Fuel Rods
A linha 1.1 introduziu lógica explícita de fuel rods e controle de criticidade. O changelog histórico informa duração de **10.000 ticks** para Uranium Fuel Rods nessa linha e ejection automática ao esgotar. Esse número é evidência histórica da implementação 1.x, mas config/runtime continua authority caso a build 1.3.2-beta.3 o altere.

## 4. Subcritical, critical e supercritical
O reator pode operar em regimes subcrítico, crítico e supercrítico conforme control rods/neutron state. Transições precisam ser server-authoritative e monotônicas por tick: modificar control rods, combustível ou coolant deve recalcular o regime sem manter flux/heat antigo em cache.

## 5. Neutron flux
Neutron flux é parte central da simulação da linha 1.x. O valor efetivo deve derivar do layout/state do reator; GUI/Ponder são apresentação. Destruir ou adicionar rod/cooler exige invalidação imediata do estado correspondente.

## 6. Coolers e reactor rod state
O changelog 1.3.0 registra correção para **reactor rod state após destruir coolers**, evidenciando um boundary real de neighbor-state invalidation. A 1.3.2-beta.3 deve manter esse fix: remover cooler não pode deixar rod visual/funcional em regime anterior.

## 7. Heat exchanger e steam
A linha 1.1 adicionou **Heat Exchanger** e cadeia de vapor. Fluido, heat e steam output devem conservar amounts e respeitar capacidade/temperature state real. Unload/restart no meio da troca não pode reaplicar o mesmo heat/steam conversion.

## 8. Turbine / geração de SU
A documentação/changelog da linha 1.x inclui geração de potência via steam/turbine e correções para capacidade maior e leitura quando a potência gerada ultrapassa capacidade máxima de SU. Create continua authority de stress units; Create Nuclear deve reportar output sem overflow, wrap ou GUI divergente.

## 9. Diamond steam/turbine capacity
A 1.3.0 ajustou capacidade de geração para variantes de alto tier, incluindo diamond steam/turbine generator. O dossiê não fixa um valor numérico não pinado aqui; o gate é capacidade e leitura coerentes, especialmente perto/acima do limite representável do network.

## 10. Uranium e processamento
O projeto publica ores e processes ligados a combustível nuclear. Registry/recipes da build são authority para itens e etapas exatas. Não inferir equivalência entre uranium de outro mod e o deste addon sem tag/recipe explícita.

## 11. Coal Dust — delta 1.3.2-beta.3
O changelog exato da build instalada refatora **Coal Dust tags e recipes** para consistência. Isso toca interoperabilidade: scripts/unifiers devem consumir a tag efetiva da build em vez de hardcode de um item específico.

## 12. Reinforced Glass — delta 1.3.2-beta.3
A mesma build atualiza **glass block tags/recipes para Reinforced Glass**. Recipe/tag resolution após datapack reload é regression gate; variantes de vidro de outros providers só entram se a tag carregada realmente as aceitar.

## 13. HEV Suit
A linha 1.3.0 introduziu **HEV Suit**, com proteção antirradiação e armor adicional via Lead Plates, além de strengths/modifiers configuráveis. Equip state, armor modifiers e radiation mitigation precisam ser decididos no servidor e removidos imediatamente ao desequipar ou quebrar peça.

## 14. HEV Suit em água
O changelog 1.3.1 corrige o HEV Suit permanecendo selado em água. Esse fix indica state ambiental/equipment sensível. Entrar/sair da água, trocar peça e relogar não devem deixar flag de sealing stale.

## 15. Hazmat suit e NBT
A linha 1.2 adicionou NBT/data state ao Hazmat suit. Conversão, dano, equip/unequip, death/relog e recipe transforms devem preservar os dados previstos sem duplicar modifiers/protection.

## 16. Radiation system
Radiação é gameplay server-side do addon. Exposição, proteção, decay/effects e fontes devem ser aplicados uma vez por state/tick conforme implementação. Client overlays/meters apenas refletem o valor authoritative e não podem remover exposição funcional.

## 17. Radiation Meter e Curios
A linha 1.2 tornou o **Radiation Meter** compatível com Curios e incluiu lógica de auto-inserção quando nenhum meter está equipado, com config para desabilitar. Curios 9.5.1 está instalado no pack, portanto este é um gate concreto. Auto-insert precisa ser idempotente e nunca gerar um segundo meter por relog/slot refresh.

## 18. Gas Centrifuge e outros processos
A linha 1.3.0 menciona correções de Ponder para Gas Centrifuge e outras máquinas. Ponder é documentação client-side; Recipe Manager/machine implementation são authority. Tutorial desatualizado não deve ser interpretado como recipe efetiva.

## 19. Auto Engineer NPC
A linha 1.2 adicionou **Auto Engineer NPC**. Spawn/trade/automation semantics exatos ficam sob runtime/source da build; o catálogo registra a existência sem inventar trades, AI goals ou frequências não pinadas.

## 20. Worldgen e mundo Oppenminer
A linha 1.2 adicionou world preset/superflat **Oppenminer** e radioactive blocks com default de geração desativado segundo changelog. Worldgen/config precisa ser validado antes de ativar em mundo existente; não assumir que blocos radioativos geram naturalmente no pack atual.

## 21. Experimental/Beta boundary
O README alerta que recursos experimentais podem ser instáveis. Isso exige smoke-tests mais estritos para reactor lifecycle, radiation, network output e world save. “Beta” é maturidade do artefato, não dúvida sobre presença/versionamento.

## 22. Create version drift
O source matching foi desenvolvido contra Create 6.0.7; o pack físico usa Create 6.0.10. A build pode funcionar nessa linha, mas hooks de stress, kinetic source, Ponder e contraption APIs precisam de regression test. Compatibilidade não é presumida só por semver próximo.

## 23. Sobreposição com New Age e Crafts & Additions
Create: New Age 1.2.0 e Create Crafts & Additions 1.7.0 também oferecem geração/conversão de energia. O overlap é parcial: Nuclear adiciona reactor/radiation/fuel chain própria. O risco real é criar loops de conversão SU/FE ou rotas energéticas com custos incompatíveis, não redundância automática.

## 24. Tags e KubeJS
Coal dust, glass e materiais nucleares podem ser afetados por tags/recipes/datapacks. KubeJS está instalado no pack. Scripts devem preservar a authority das tags da build e evitar ida-e-volta de materiais por providers que resulte em duplicação.

## 25. Client/server e multiplayer
Reactor simulation, neutron flux, fuel depletion, steam, SU output, radiation, armor protection e inventory mutation são server-authoritative. GUI, Ponder, particles e meter presentation são client-facing. Dois clientes não podem acionar fuel ejection ou control mutation duas vezes.

## 26. Lifecycle
Testar formation/desformation, control rod changes, fuel insertion/ejection, cooler removal, chunk unload/reload, restart com reactor ativo, steam buffer, radiation exposure, equip/unequip HEV/Hazmat, Curios meter auto-insert, `/reload` de tags/recipes e worldgen em chunks novos.

## 27. Riscos
1. Reactor mantém rod/flux state stale após remover cooler.
2. Fuel rod é ejetado/consumido duas vezes no boundary de depletion.
3. Criticality muda sem recalcular neutron flux.
4. Heat exchanger duplica/perde fluid/steam em unload.
5. Geração de SU overflowa ou é reportada incorretamente.
6. Coal Dust tag refactor deixa recipe antiga órfã.
7. Reinforced Glass aceita/rejeita variante errada por tag drift.
8. HEV/Hazmat protection permanece ativa após desequipar.
9. HEV sealing em água reproduz bug corrigido.
10. Radiation Meter auto-insert duplica item em relog/Curios refresh.
11. Create 6.0.10 altera hook cinético esperado pela build 6.0.7-dev.
12. Conversão com New Age/CC&A cria loop de energia.
13. Beta/experimental state corrompe save ao reiniciar reactor ativo.
14. Conteúdo V2 posterior é indevidamente atribuído à linha 1.3.2-beta.3.

## 28. Matriz de testes
- [ ] Dedicated server inicia com Nuclear 1.3.2-beta.3 + Create 6.0.10.
- [ ] Reactor forma/desforma sem state fantasma.
- [ ] Control rods alteram regime/flux conforme implementação.
- [ ] Fuel depletion/ejection ocorre uma única vez.
- [ ] Remover cooler atualiza reactor rod state conforme fix herdado.
- [ ] Heat Exchanger conserva fluid/steam em restart.
- [ ] Turbine/generator reporta SU sem overflow em alto output.
- [ ] Coal Dust recipes/tags 1.3.2 resolvem após `/reload`.
- [ ] Reinforced Glass recipes/tags resolvem variantes esperadas.
- [ ] HEV/Hazmat protection liga/desliga apenas com equipamento válido.
- [ ] HEV não mantém sealing stale ao entrar/sair da água.
- [ ] Radiation Meter Curios auto-insert não duplica item e respeita config.
- [ ] Coexistência com New Age/CC&A não cria ciclo positivo de energia.
- [ ] Save/restart com reactor ativo preserva state sem replay.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 29. Evidências e limites
A modlist física confirma JAR/mod id/runtime 1.3.2-beta.3. O source oficial `main` matching confirma versão 1.3.2-beta.3, MC 1.21.1, Create dev 6.0.7 e escopo de ores/reactor/electricity. README/changelog da mesma linha confirmam reactor control, fuels, neutron/steam systems, radiation, HEV/Hazmat, Curios meter e os deltas exatos 1.3.2 de Coal Dust/Reinforced Glass. Conteúdo de V2 posterior não foi usado como evidência desta build.

> 🔒 **Boundary canônico:** Create Nuclear owns reactor/radiation/fuel state; Create owns stress/kinetics. Reactor state deve ser recalculável e conservativo, e nenhum dado de linhas V2 posteriores deve contaminar a catalogação da 1.3.2-beta.3.
